package com.extollit.collect;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MadMartianMod
 * <p/>
 * Created by jonathan on 29/07/16.
 */
public abstract class FilterIterable<E> implements Iterable<E> {
    protected final Iterable<E> delegate;

    public FilterIterable(Iterable<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Iterator<E> iterator() {
        return new IterImpl(this.delegate.iterator());
    }

    protected class IterImpl extends Iter<E> {
        private final Iterator<E> delegate;

        protected IterImpl(Iterator<E> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected E findNext() {
            E next;
            while (this.delegate.hasNext()) {
                next = this.delegate.next();
                if (shouldHave(next))
                    return next;
            }

            return null;
        }

        protected boolean shouldHave(E next) {
            return FilterIterable.this.shouldHave(next);
        }
    }

    protected abstract boolean shouldHave(E element);

    public abstract static class Iter<E> implements Iterator<E> {
        private E current;
        private boolean dead = false;

        protected abstract E findNext();

        @Override
        public final boolean hasNext() {
            if (this.dead)
                return false;

            if (this.current == null)
                this.current = findNext();
            this.dead = this.current == null;
            return !this.dead;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException(
                        MessageFormat.format("No more elements exist in this iterator {0}", this)
                );

            E result = this.current;
            this.current = findNext();
            this.dead = this.current == null;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
