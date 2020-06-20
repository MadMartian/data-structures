package com.extollit.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MadMartianMod
 * <p/>
 * Created by jonathan on 29/07/16.
 */
public class RingIterator<T> implements Iterator<T> {
    private final Iterable<T> iterable;

    private Iterator<T> delegate, delegate0;
    private boolean available;

    public RingIterator(Iterable<T> iterable) {
        this.iterable = iterable;
        reset();
    }

    public void reset() {
        this.delegate0 = null;
        this.delegate = this.iterable.iterator();
        this.available = this.delegate.hasNext();
    }

    @Override
    public boolean hasNext() {
        if (!this.delegate.hasNext())
            return this.available = (this.delegate = this.iterable.iterator()).hasNext();

        return this.available;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();

        try {
            return this.delegate.next();
        } finally {
            this.delegate0 = this.delegate;
        }
    }

    @Override
    public void remove() {
        if (this.delegate0 == null)
            throw new IllegalStateException();
        this.delegate0.remove();
    }
}
