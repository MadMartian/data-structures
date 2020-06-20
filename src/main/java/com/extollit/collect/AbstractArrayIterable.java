package com.extollit.collect;

import java.text.MessageFormat;
import java.util.Iterator;

/**
 * MadMartianMod
 * <p/>
 * Created by jonathan on 29/07/16.
 */
abstract class AbstractArrayIterable<A, B> implements Iterable<A> {
    public final B[] delegate;

    protected abstract static class AbstractIter<A, B> implements Iterator<A> {
        private final B[] array;
        private final int len;

        private int c = 0;

        public AbstractIter(B[] array) {
            this(array, array.length);
        }
        public AbstractIter(B[] array, final int len) {
            if (len > array.length || len < 0)
                throw new ArrayIndexOutOfBoundsException(
                    MessageFormat.format("Iteration limit is not within bounds! 0 <= len < {0} but len = {1}", array.length, len)
                );

            this.array = array;
            this.len = len;
        }

        @Override
        public final boolean hasNext() {
            return this.c < this.len;
        }

        @Override
        public A next() {
            return map(this.c, this.array[this.c++]);
        }

        protected abstract A map(int index, B input);

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public AbstractArrayIterable(B[] elements) {
        this.delegate = elements;
    }

}
