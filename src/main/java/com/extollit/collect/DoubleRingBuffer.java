/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.text.MessageFormat;
import java.util.Arrays;

public final class DoubleRingBuffer {
    private final double[] buffer;
    private int i = 0;

    @SuppressWarnings("unchecked")
    public DoubleRingBuffer(int size) {
        this.buffer = new double[size];
    }

    public double insert(double value)
    {
        double value0 = buffer[i];
        buffer[i++] = value;
        if (i >= buffer.length)
            i = 0;

        return value0;
    }

    public int size() {
        return buffer.length;
    }

    public boolean isEmpty() {
        return this.i == 0;
    }

    public boolean contains(double o) {
        for (int c = 0; c < this.i; ++c)
            if (o == this.buffer[c])
                return true;

        return false;
    }

    public class Iterator {
        private int i = 0;

        public boolean hasNext() {
            return DoubleRingBuffer.this.buffer.length > 0;
        }

        public double next() {
            if (this.i >= DoubleRingBuffer.this.buffer.length)
                this.i = 0;

            return DoubleRingBuffer.this.buffer[this.i++];
        }

        public void remove() {
            DoubleRingBuffer.this.remove(this.i);
        }
    }

    public double sum() {
        double sum = 0;
        final int size = size();
        final int i = this.i;
        final double[] buffer = this.buffer;

        for (int c = 0; c < size; ++c)
            sum += buffer[(i + c) % size];

        return sum;
    }

    public double average() {
        return sum() / size();
    }

    public Iterator iterator() {
        return new Iterator();
    }

    public double[] toArray() {
        return Arrays.copyOf(this.buffer, this.i);
    }

    public boolean add(double t) {
        insert(t);
        return true;
    }

    private void remove(int i) {
        if (i < this.i && i >= 0) {
            final int len = this.i-- - i;
            if (len > 0)
                System.arraycopy(this.buffer, i + 1, this.buffer, i, len);
        }
        else
            throw new IndexOutOfBoundsException(MessageFormat.format("The index {0} was out of bounds", i));
    }

    public boolean remove(double o) {
        for (int c = 0; c < this.i; ++c)
            if (o == this.buffer[c]) {
                remove(c);
                return true;
            }

        return false;
    }

    public void clear() {
        this.i = 0;
    }
}
