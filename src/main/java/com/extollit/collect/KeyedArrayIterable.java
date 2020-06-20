package com.extollit.collect;

import java.util.Arrays;
import java.util.Iterator;

/**
 * MadMartianMod
 * <p/>
 * Created by jonathan on 29/07/16.
 */
public final class KeyedArrayIterable<T> extends AbstractArrayIterable<KeyedArrayIterable.Entry<T>, T> {

    public static final class Entry<T> {
        public final int index;
        public final T element;

        public Entry(int index, T element) {
            this.index = index;
            this.element = element;
        }

        @Override
        public String toString() {
            return this.index + " -> " + this.element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?> entry = (Entry<?>) o;

            if (index != entry.index) return false;
            return element != null ? element.equals(entry.element) : entry.element == null;

        }

        @Override
        public int hashCode() {
            return index;
        }

        public <V> Entry<V> with(V value) {
            return new Entry<V>(this.index, value);
        }
    }

    public KeyedArrayIterable(T[] elements) {
        super(elements);
    }

    @Override
    public Iterator<Entry<T>> iterator() {
        return new Iter<T>(this.delegate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyedArrayIterable<?> that = (KeyedArrayIterable<?>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(delegate, that.delegate);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(delegate);
    }

    public static class Iter<T> extends AbstractIter<Entry<T>, T> {
        public Iter(T[] array) {
            super(array);
        }

        @Override
        protected final Entry<T> map(int index, T input) {
            return new Entry<T>(index, input);
        }
    }
}
