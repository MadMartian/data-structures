package com.extollit.collect;

import java.util.Arrays;
import java.util.Iterator;

/**
 * MadMartian Mod
 *
 * Created by jonathann on 15-10-29.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class FlattenIterable<T> implements Iterable<T> {
    private final Iterable<? extends Iterable<? extends T>> delegates;

    public FlattenIterable(Iterable<? extends T> first, Iterable<? extends T> second)
    {
        this(Arrays.asList(first, second));
    }
    public FlattenIterable(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third)
    {
        this(Arrays.asList(first, second, third));
    }
    public FlattenIterable(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth)
    {
        this(Arrays.asList(first, second, third, fourth));
    }
    public FlattenIterable(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth, Iterable<? extends T> fifth)
    {
        this(Arrays.asList(first, second, third, fourth, fifth));
    }
    public FlattenIterable(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth, Iterable<? extends T> fifth, Iterable<? extends T> sixth)
    {
        this(Arrays.asList(first, second, third, fourth, fifth, sixth));
    }
    public FlattenIterable(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth, Iterable<? extends T> fifth, Iterable<? extends T> sixth, Iterable<? extends T> seventh)
    {
        this(Arrays.asList(first, second, third, fourth, fifth, sixth, seventh));
    }

    public FlattenIterable(Iterable<? extends Iterable<? extends T>> delegates)
    {
        this.delegates = delegates;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(this.delegates.iterator());
    }

    public static class Iter<T> extends FilterIterable.Iter<T> {
        private final Iterator<? extends Iterable<? extends T>> iterator;

        private Iterator<? extends T> current;

        public Iter(Iterator<? extends Iterable<? extends T>> iterator) {
            this.iterator = iterator;
        }

        @Override
        protected T findNext() {
            Iterator<? extends T> currentIterator = acquire();
            if (currentIterator != null && currentIterator.hasNext())
                return currentIterator.next();

            return null;
        }

        private Iterator<? extends T> acquire() {
            while ((this.current == null || !this.current.hasNext()) && this.iterator.hasNext()) {
                this.current = this.iterator.next().iterator();
            }
            return this.current;
        }
    }
}
