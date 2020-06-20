package com.extollit.collect.cache;

import java.lang.ref.Reference;
import java.text.MessageFormat;
import java.util.*;

abstract class AbstractReferenceIterable< V, R extends Reference< V >> implements Iterable< V > {
    private static final class Purge extends AbstractPurgeThread< AbstractReferenceIterable< ?, ? >> {
        public Purge() {
            super("extollIT Iterable - Purge");
        }

        @Override
        protected int cull(int offset, AbstractReferenceIterable<?, ?> iterable) {
            return iterable.cull(offset, 100);
        }
    }

    private static final Purge PURGE_THREAD = new Purge();

    static {
        PURGE_THREAD.start();
    }

    private final LinkedList< R > backing = new LinkedList<R>();

    public AbstractReferenceIterable() {
        this(Collections.<V>emptyList());
    }
    public AbstractReferenceIterable(Collection< V > init) {
        for (V value : init)
            add(value);

        PURGE_THREAD.register(this);
    }

    private synchronized int cull(int offset, int batch) {
        final LinkedList<R> backing = this.backing;
        final Iterator<R> i = backing.iterator();
        {
            final int size = backing.size();
            if (size > 0)
                offset = offset % size;
        }

        int fin = offset;

        while (offset-- > 0)
            i.next();

        while (i.hasNext() && batch-- > 0) {
            Reference<? extends V> ref = i.next();
            if (ref.get() == null)
                i.remove();

            ++fin;
        }
        return batch > 0 ? 0 : fin;
    }

    private final class Iter implements Iterator< V > {
        private final ListIterator< R > i = AbstractReferenceIterable.this.backing.listIterator();

        private V current;

        protected V findNext() {
            final ListIterator<R> i = this.i;
            while (i.hasNext()) {
                final V value = i.next().get();
                if (value != null)
                    return value;
                else
                    i.remove();
            }

            return null;
        }

        @Override
        public final boolean hasNext() {
            if (this.current == null)
                this.current = findNext();
            return this.current != null;
        }

        @Override
        public V next() {
            if (!hasNext())
                throw new NoSuchElementException(
                        MessageFormat.format("No more elements exist in this iterator {0}", this)
                );

            V result = this.current;
            this.current = findNext();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public final Iterator<V> iterator() {
        if (this.backing.isEmpty())
            return Collections.emptyIterator();
        else
            return new Iter();
    }

    public final void add(V value) {
        this.backing.add(reference(value));
    }

    protected abstract R reference(V value);
}
