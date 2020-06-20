package com.extollit.tuple;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * MadMartian Mod
 *
 * Created by MadMartian on 4/16/2015.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class SymmetricPair< T > extends Pair< T, T > implements PairContract< T >
{
    public static class Sealed< T > extends Pair.Sealed<T, T> implements PairContract<T> {
        public Sealed(T lesser, T greater) {
            super(lesser, greater);
        }

        public static < T > Sealed<T> combination(T one, T two) {
            if (one == two || one.hashCode() < two.hashCode())
                return new Sealed<T>(one, two);
            else
                return new Sealed<T>(two, one);
        }

        public static < T > Sealed<T> ordered(T first, T second) {
            return new Sealed<T>(first, second);
        }

        @Override
        public T noneOf(T test)
        {
            return SymmetricPair.noneOf(super.left, super.right, test);
        }

        @Override
        public T allOf(T test)
        {
            return SymmetricPair.allOf(super.left, super.right, test);
        }

        @Override
        public Iterator<T> iterator()
        {
            return new IteratorView<T>(this.left, this.right);
        }

        @Override
        public Set<T> toSet()
        {
            return new SetView<T>(this.left, this.right);
        }

        @Override
        public boolean contains(T test) {
            return test.equals(this.left) || test.equals(this.right);
        }
    }

    public SymmetricPair(T lesser, T greater)
    {
        super(lesser, greater);
    }

    public static <T> SymmetricPair<T> combination(T one, T two)
    {
        if (one == two || one.hashCode() < two.hashCode())
            return new SymmetricPair<T>(one, two);
        else
            return new SymmetricPair<T>(two, one);
    }

    public static < T > SymmetricPair<T> ordered(T first, T second) {
        return new SymmetricPair<T>(first, second);
    }

    @Override
    public Sealed<T> sealed() { return new Sealed<T>(super.left, super.right); }

    private static <T> T noneOf(T left, T right, T test) {
        if (test.equals(left))
            return right;
        else if (test.equals(right))
            return left;
        else
            return null;
    }

    private static <T> T allOf(T left, T right, T test) {
        if (test.equals(left))
            return left;
        else if (test.equals(right))
            return right;
        else
            return null;
    }

    @Override
    public T noneOf(T test)
    {
        return noneOf(this.left, this.right, test);
    }

    @Override
    public T allOf(T test)
    {
        return allOf(this.left, this.right, test);
    }

    public static final class SetView<T> extends AbstractCollection<T> implements Set<T> {

        private final T left, right;

        public SetView(T left, T right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int size() { return 2; }

        @Override
        public boolean contains(Object o) {
            return this.left == o || this.right == o || (
                o != null && (o.equals(this.left) || o.equals(this.right))
            );
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Set<?>)
            {
                Set<?> otherSet = (Set<?>) obj;
                return otherSet.size() == 2 && otherSet.contains(this.left) && otherSet.contains(this.right);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.left.hashCode() + this.right.hashCode();
        }

        @Override
        public Iterator<T> iterator() { return new IteratorView<T>(this.left, this.right); }

        @Override
        public Object[] toArray() {
            return new Object[] { this.left, this.right };
        }
    }

    @Override
    public Set<T> toSet()
    {
        return new SetView<T>(this.left, this.right);
    }

    protected static final class IteratorView<T> implements Iterator<T> {
        private final T left, right;

        private T current;

        public IteratorView(T left, T right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean hasNext()
        {
            return this.current != this.right;
        }

        @Override
        public T next()
        {
            if (this.current == null)
                this.current = this.left;
            else if (this.current == this.left)
                this.current = this.right;
            else
                throw new NoSuchElementException();

            return this.current;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return new IteratorView<T>(this.left, this.right);
    }

    @Override
    public boolean contains(T test) {
        return test.equals(this.left) || test.equals(this.right);
    }
}

interface PairContract< T > extends Iterable< T > {
    T noneOf(T test);
    T allOf(T test);
    Set<T> toSet();

    boolean contains(T test);
}