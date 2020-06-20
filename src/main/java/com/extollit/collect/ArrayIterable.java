package com.extollit.collect;

import java.util.Arrays;
import java.util.Iterator;

/**
 * MadMartianMod
 * <p/>
 * Created by jonathan on 29/07/16.
 */
public final class ArrayIterable<T> extends AbstractArrayIterable<T, T> {
    public ArrayIterable(T[] elements) {
        super(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter<T>(this.delegate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayIterable<?> that = (ArrayIterable<?>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(delegate, that.delegate);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(delegate);
    }

    public static class Iter<T> extends AbstractIter<T, T> {
        public Iter(T[] array) {
            super(array);
        }
        public Iter(T[] array, int len) {
            super(array, len);
        }

        @Override
        protected final T map(int index, T input) {
            return input;
        }
    }
}
