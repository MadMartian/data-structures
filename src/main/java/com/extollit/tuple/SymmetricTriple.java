package com.extollit.tuple;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 19/02/16.
 *
 * Copyright (c) 2016 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class SymmetricTriple< T > extends Triple< T, T, T > implements TripleContract< T > {
    public static class Sealed< T > extends Triple.Sealed<T, T, T> implements TripleContract<T> {
        public Sealed(T greatest, T meh, T lessest) {
            super(greatest, meh, lessest);
        }

        public static <T> Sealed<T> combination(T one, T two, T three)
        {
            if (one.hashCode() < two.hashCode())
                if (two.hashCode() < three.hashCode())
                    return new Sealed<T>(one, two, three);
                else if (three.hashCode() < one.hashCode())
                    return new Sealed<T>(three, one, two);
                else
                    return new Sealed<T>(one, three, two);
            else
            if (three.hashCode() < two.hashCode())
                return new Sealed<T>(three, two, one);
            else if (three.hashCode() < one.hashCode())
                return new Sealed<T>(two, three, one);
            else
                return new Sealed<T>(two, one, three);
        }

        public static < T > Sealed<T> ordered(T one, T two, T three) {
            return new Sealed<T>(one, two, three);
        }

        @Override
        public Iterator<T> iterator()
        {
            return new IteratorView<T>(this.first, this.second, this.third);
        }

        @Override
        public Set<T> toSet()
        {
            return new SetView<T>(this.first, this.second, this.third);
        }
    }

    private SymmetricTriple(T greatest, T meh, T lessest)
    {
        super(greatest, meh, lessest);
    }

    public static <T> SymmetricTriple<T> combination(T one, T two, T three)
    {
        if (one.hashCode() < two.hashCode())
            if (two.hashCode() < three.hashCode())
                return new SymmetricTriple<T>(one, two, three);
            else if (three.hashCode() < one.hashCode())
                return new SymmetricTriple<T>(three, one, two);
            else
                return new SymmetricTriple<T>(one, three, two);
        else
        if (three.hashCode() < two.hashCode())
            return new SymmetricTriple<T>(three, two, one);
        else if (three.hashCode() < one.hashCode())
            return new SymmetricTriple<T>(two, three, one);
        else
            return new SymmetricTriple<T>(two, one, three);
    }


    public static < T > SymmetricTriple<T> ordered(T one, T two, T three) {
        return new SymmetricTriple<T>(one, two, three);
    }

    @Override
    public Sealed<T> sealed() { return new Sealed<T>(super.first, super.second, super.third); }

    public static final class SetView<T> extends AbstractCollection<T> implements Set<T> {
        private final T first, second, third;

        public SetView(T first, T second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public int size() { return 2; }

        @Override
        public boolean contains(Object o) {
            return this.first == o || this.second == o || this.third == o || (
                o != null && (o.equals(this.first) || o.equals(this.second) || o.equals(this.third))
            );
        }

        @Override
        public Iterator<T> iterator() { return new IteratorView<T>(this.first, this.second, this.third); }

        @Override
        public Object[] toArray() {
            return new Object[] { this.first, this.second, this.third };
        }
    }

    @Override
    public Set<T> toSet()
    {
        return new SetView<T>(this.first, this.second, this.third);
    }

    protected static final class IteratorView<T> implements Iterator<T> {
        private final T first, second, third;

        private T current;

        public IteratorView(T first, T second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public boolean hasNext()
        {
            return this.current != this.third;
        }

        @Override
        public T next()
        {
            if (this.current == null)
                this.current = this.first;
            else if (this.current == this.first)
                this.current = this.second;
            else if (this.current == this.second)
                this.current = this.third;
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
        return new IteratorView<T>(this.first, this.second, this.third);
    }
}

interface TripleContract< T > extends Iterable< T > {
    Set<T> toSet();
}