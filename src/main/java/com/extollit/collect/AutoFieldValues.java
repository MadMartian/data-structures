/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * main
 * <p/>
 * Created by jonathan on 26/11/16.
 */
public class AutoFieldValues<T> extends AbstractList<T> implements List<T> {
    private final T[] values;

    @SuppressWarnings("unchecked")
    public AutoFieldValues(Class<?> clazz, Class<T> elementType, Pattern pattern) throws IllegalAccessException {
        final Field[] fields = clazz.getFields();
        final T[] values = (T[]) new Object[fields.length];

        int c = 0;
        for (Field field : fields) {
            if (pattern.matcher(field.getName()).matches()) {
                final int modifiers = field.getModifiers();
                if (elementType.isAssignableFrom(field.getType()) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers))
                    values[c++] = (T) field.get(null);
            }
        }
        this.values = values;
    }

    @Override
    public int size() {
        return this.values.length;
    }

    @Override
    public boolean isEmpty() {
        return this.values.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T value : this.values)
            if (o.equals(value))
                return true;

        return false;
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        return this.values[index];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        for (int c = 0; c < this.values.length; ++c) {
            if (o.equals(this.values[c]))
                return c;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int c = this.values.length - 1; c >= 0; --c) {
            if (o.equals(this.values[c]))
                return c;
        }

        return -1;
    }
}
