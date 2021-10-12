package com.extollit.collect;

import java.util.Iterator;

/**
 * MadMartianMod
 *
 * Created by jonathan on 29/07/16.
 */
public abstract class AdapterIterable<A, B> implements Iterable<A>, ITypeMappable< A, B > {
    private final Iterable<B> delegate;

    public AdapterIterable(Iterable<B> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Iterator<A> iterator() {
        return new AdapterIterator<A, B>(this.delegate.iterator()) {
            @Override
            protected A map(B input) {
                return AdapterIterable.this.map(input);
            }
        };
    }

    public abstract static class AdapterIterator<A, B> implements Iterator<A> {
        private final Iterator<B> delegate;

        protected AdapterIterator(Iterator<B> delegate) {
            this.delegate = delegate;
        }

        @Override
        public void remove() {
            this.delegate.remove();
        }

        @Override
        public A next() {
            return map(this.delegate.next());
        }

        protected abstract A map(B input);

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }
    }
}
