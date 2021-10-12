/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.text.MessageFormat;

/**
 * main
 *
 * Created by jonathan on 28/11/16.
 */
public class IntList {
    private static final int GROW = 10;

    private int [] array;
    private int size;

    public IntList() {
        this(GROW);
    }
    public IntList(int reserved) {
        this(new int[reserved]);
    }
    public IntList(int[] array) {
        this.array = array;
        this.size = array.length;
    }

    private void growBy(int grow) {
        int [] newArray = new int[array.length + grow];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public void insert(int value, int i) {
        if (size >= array.length)
            growBy(GROW);

        System.arraycopy(array, i, array, i + 1, size++);
        array[i] = value;
    }
    public int at(int i, int value) {
        boundsCheck(i);
        final int value0 = array[i];
        array[i] = value;
        return value0;
    }
    public void add(int i) {
        if (size >= array.length)
            growBy(GROW);

        array[size++] = i;
    }

    public int removeAt(int i) {
        boundsCheck(i);
        final int result = array[i];
        System.arraycopy(array, i + 1, array, i, size-- - (i + 1));
        return result;
    }

    private void boundsCheck(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException(MessageFormat.format("{0} is out of bounds, size is {1}", i, size));
    }

    public final int size() { return size; }
    public final int at(int i) {
        boundsCheck(i);
        return array[i];
    }

    public void clear() {
        size = 0;
    }

    public boolean empty() {
        return size == 0;
    }

    public void remove(int data) {
        for (int c = 0; c < size; ++c)
            if (array[c] == data)
            {
                removeAt(c);
                break;
            }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        final int size = this.size;
        sb.append(size);
        sb.append(" ~ ");
        sb.append('[');
        for (int c = 0; c < size; ++c) {
            sb.append(at(c));
            sb.append(", ");
        }
        if (size > 0)
            sb.delete(sb.length() - 2, sb.length());
        sb.append(']');
        return sb.toString();
    }
}
