/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.*;

public final class RingBuffer< T > extends AbstractCollection< T > implements Collection< T >, List< T > {
    private final T[] buffer;
    private int i = 0, len;

    @SuppressWarnings("unchecked")
    public RingBuffer(int size) {
        this.buffer = (T[]) Array.newInstance(Object.class, size);
    }

    private int physicalIndex(int index) {
        return (index - (len - i) + buffer.length) % buffer.length;
    }

    private int virtualIndex(int index) {
        return index < i ? len - (i - index) : index - i - (buffer.length - len);
    }

    public T append(T value)
    {
        T value0 = buffer[i];
        buffer[i++] = value;
        inc();

        return value0;
    }

    private void inc() {
        if (len < buffer.length)
            len++;

        if (i >= buffer.length)
            i = 0;
    }

    private void dec() {
        len--;

        if (i < 0)
            i = len;
    }

    public int size() {
        return len;
    }

    @Override
    public boolean isEmpty() {
        return this.len == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int c = 0; c < this.i; ++c)
            if (o.equals(this.buffer[c]))
                return true;

        return false;
    }

    private class RingIterator implements ListIterator<T> {
        private int
                virtualIndex,
            virtualIndex0;

        public RingIterator(int index) {
            this.virtualIndex = index;
        }

        @Override
        public boolean hasNext() {
            return this.virtualIndex < RingBuffer.this.len;
        }

        @Override
        public boolean hasPrevious() {
            return this.virtualIndex > 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            return (T) RingBuffer.this.buffer[physicalIndex(virtualIndex0 = virtualIndex++)];
        }

        @SuppressWarnings("unchecked")
        @Override
        public T previous() {
            return (T) RingBuffer.this.buffer[physicalIndex(virtualIndex0 = virtualIndex--)];
        }

        @Override
        public int nextIndex() {
            return this.virtualIndex0 + 1;
        }

        @Override
        public int previousIndex() {
            return this.virtualIndex0 - 1;
        }

        @Override
        public void remove() {
            RingBuffer.this.remove(physicalIndex(this.virtualIndex0));
        }

        @Override
        public void set(T t) {
            RingBuffer.this.buffer[physicalIndex(this.virtualIndex0)] = t;
        }

        @Override
        public void add(T t) {
            RingBuffer.this.add(physicalIndex(this.virtualIndex0), t);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RingIterator(0);
    }

    @Override
    public Object[] toArray() {
        final Object [] result = new Object[len];
        final int v = len - i;
        System.arraycopy(buffer, buffer.length - v, result, 0, v);
        System.arraycopy(buffer, 0, result, v, i);
        return result;
    }

    @Override
    public boolean add(T t) {
        append(t);
        return true;
    }

    public T remove(int index) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));

        return removePhysical(physicalIndex(index));
    }

    private T removePhysical(int index) {
        final T value0 = buffer[index];

        if (index >= i)
            System.arraycopy(buffer, i, buffer, i + 1, index - i);
        else
            System.arraycopy(buffer, index + 1, buffer, index, i-- - index);

        dec();
        return value0;
    }

    private String outOfBoundsMessage(int i) {
        return MessageFormat.format("Index of {0} is not within 0 <= x < {1}", i, len);
    }

    @Override
    public int indexOf(Object o) {
        for (int c = 0; c < i; ++c)
            if (o.equals(this.buffer[c]))
                return virtualIndex(c);

        for (int c = buffer.length - (len - i); c < buffer.length; ++c)
            if (o.equals(buffer[c]))
                return virtualIndex(c);

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i;
        for (i = this.buffer.length - 1; i >= 0; --i) {
            if (this.buffer[i] != null && this.buffer[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new RingIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new RingIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        for (int c = 0; c < i; ++c)
            if (o.equals(this.buffer[c])) {
                removePhysical(c);
                return true;
            }

        for (int c = buffer.length - (len - i); c < buffer.length; ++c)
            if (o.equals(buffer[c])) {
                removePhysical(c);
                return true;
            }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> other) {
        for (Object o : other) {
            boolean found = false;
            for (int c = 0; c < i; ++c)
                found |= o.equals(this.buffer[c]);

            for (int c = buffer.length - (len - i); c < buffer.length; ++c)
                found |= o.equals(this.buffer[c]);

            if (!found)
                return false;
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> other) {
        boolean flag = false;
        for (Object o : other)
            flag |= remove(o);

        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> other) {
        boolean modified = false;
        for (int c = 0; c < i; ++c)
            if (!other.contains(this.buffer[c])) {
                removePhysical(c--);
                modified = true;
            }

        for (int c = buffer.length - (len - i); c < buffer.length; ++c)
            if (!other.contains(this.buffer[c])) {
                removePhysical(c);
                modified = true;
            }

        return modified;
    }

    @Override
    public void clear() {
        for (int c = 0; c < i; ++c)
            buffer[c] = null;
        len = i = 0;
    }

    @Override
    public T get(int index) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));

        return buffer[physicalIndex(index)];
    }

    @Override
    public T set(int index, T element) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));

        final int pi = physicalIndex(index);
        final T value0 = buffer[pi];
        buffer[pi] = element;
        return value0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(int index, Collection<? extends T> source) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));

        final T [] array = (T[]) source.toArray();
        final int copy = Math.min(buffer.length, Math.min(index + (buffer.length - len), array.length));
        final T [] temp = (T[]) new Object[Math.min(buffer.length, copy + len)];

        int ti = temp.length,
            rpi = i - 1;

        for (int c = len - index; c > 0; --c, --rpi)
            temp[--ti] = buffer[(rpi + buffer.length) % buffer.length];

        System.arraycopy(array, array.length - copy, temp, ti -= copy, copy);
        for (int c = temp.length - copy - Math.max(0, len - index); c > 0; --c, --rpi)
            temp[--ti] = buffer[(rpi + buffer.length) % buffer.length];

        System.arraycopy(temp, 0, buffer, 0, len = temp.length);
        i = len % buffer.length;

        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));

        int pi = physicalIndex(index);

        if (pi >= i)
            System.arraycopy(buffer, i + 1, buffer, i, pi - i);
        else
            System.arraycopy(buffer, pi, buffer, pi + 1, i++ - pi);

        buffer[pi] = element;
        inc();
    }
}
