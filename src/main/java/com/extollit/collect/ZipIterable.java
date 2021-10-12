/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import com.extollit.tuple.Pair;

import java.util.Iterator;

/**
 * main
 *
 * Created by jonathan on 14/05/16.
 */
public class ZipIterable< L, R > implements Iterable< Pair.Sealed< L, R > > {
    private final Iterable<? extends L> left;
    private final Iterable<? extends R> right;

    public ZipIterable(Iterable<? extends L> left, Iterable<? extends R> right)
    {
        this.left = left;
        this.right = right;
    }

    @Override
    public Iterator<Pair.Sealed<L, R>> iterator() {
        return new Iter(this.left.iterator(), this.right.iterator());
    }

    private class Iter implements Iterator<Pair.Sealed<L, R>> {
        private final Iterator<? extends L> left;
        private final Iterator<? extends R> right;

        public Iter(Iterator<? extends L> left, Iterator<? extends R> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public final boolean hasNext() {
            return this.left.hasNext() && this.right.hasNext();
        }

        @Override
        public final Pair.Sealed<L, R> next() {
            return Pair.Sealed.of(this.left.next(), this.right.next());
        }

        @Override
        public void remove() {
            this.left.remove();
            this.right.remove();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Pair.Sealed<L, R> value : this) {
            sb.append(value);
            sb.append(", ");
        }
        if (sb.length() > 1)
            sb.delete(sb.length() - 2, sb.length());
        sb.append(']');
        return sb.toString();
    }
}
