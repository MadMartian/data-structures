package com.extollit.collect.cache;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractPurgeThread< O > extends Thread {
    private static final int
            MAX_SLEEP_TIME = 1000,
            MIN_SLEEP_TIME = 1;

    private final List<WeakReference<O>> objects = new LinkedList<WeakReference<O>>();

    private volatile boolean running = true;

    private ListIterator<WeakReference<O>> iterator;
    private WeakReference<O> current;

    public AbstractPurgeThread(String name) {
        super(name);
        setPriority(MIN_PRIORITY + 1);
        setDaemon(true);
        this.iterator = this.objects.listIterator();
    }

    @Override
    public void run() {
        int offset = 0;

        try {
            while (running) {
                try {
                    int size;
                    synchronized (this) {
                        size = objects.size();
                    }
                    Thread.sleep(size == 0 ? MAX_SLEEP_TIME : Math.max(MIN_SLEEP_TIME, MAX_SLEEP_TIME / size));

                    {
                        final O object;
                        synchronized (this) {
                            Iterator<WeakReference<O>> i = this.iterator;
                            WeakReference<O> weakCache = this.current;
                            if (weakCache == null) {
                                if (!i.hasNext())
                                    i = this.iterator = this.objects.listIterator();

                                if (!i.hasNext())
                                    continue;

                                weakCache = this.current = i.next();
                            }

                            object = weakCache.get();
                            if (object == null) {
                                i.remove();
                                this.current = null;
                            }
                        }

                        if (object != null) {
                            offset = cull(offset, object);
                            if (offset == 0)
                                this.current = null;
                        }
                    }
                } catch (InterruptedException e) {
                    synchronized (this) {
                        if (!this.running)
                            break;
                    }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    protected abstract int cull(int offset, O object);

    public synchronized void quit() {
        this.running = false;
        interrupt();
        try {
            join(3000);
        } catch (InterruptedException ignored) {}
    }

    public synchronized void register(O object) {
        final int index0 = this.iterator.nextIndex();
        this.objects.add(new WeakReference<O>(object));
        if (index0 >= this.objects.size())
            this.iterator = this.objects.listIterator();
        else
            this.iterator = this.objects.listIterator(index0);
    }
}
