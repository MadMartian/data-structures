package com.extollit.collect;

import com.extollit.misc.Either;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 16/01/16.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class Option<T> extends AbstractCollection<T> implements IOption<T> {
    private T element;

    public Option () {}
    public Option(T element) {
        this.element = element;
    }

    public static < T > Option<T> of (T element) {
        return new Option<T>(element);
    }
    public static < T > IOption<T> lazy (Iterator<T> iterator) {
        return new LazyOption<T>(iterator);
    }
    public static < T > IOption<T> pure (Iterable<T> source) {
        return new PureOption<T>(source);
    }
    public static < T > Option<T> none() {
        return new Option<T>();
    }

    public static < T > T get(Option<T> option) { return option == null ? null : option.get(); }

    public final T get() { return this.element; }

    @Override
    public IOption<T> or(IOption<T> other) {
        return this.element == null ? other : this;
    }

    @Override
    public int size() {
        return this.element == null ? 0 : 1;
    }

    @Override
    public boolean isEmpty() {
        return this.element == null;
    }

    @Override
    public boolean contains(Object o) {
        return this.element != null && this.element.equals(o);
    }

    public final Option<T> or(Option<T> other) {
        return isEmpty() ? other : this;
    }

    @Override
    public Iterator<T> iterator() {
        return new OptionIterator(this.element);
    }

    @Override
    public Object[] toArray() {
        return this.element == null ? new Object[0] : new Object[] { this.element };
    }

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            this.element = value;
            return true;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean remove(Object o) {
        if (this.element != null && this.element.equals(o)) {
            this.element = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (this.element == null)
            return c.isEmpty();

        if (c.size() == 1)
            return c.iterator().next().equals(this.element);

        return false;
    }

    @Override
    public void clear() {
        this.element = null;
    }

    private class OptionIterator implements Iterator<T> {
        private T element;

        public OptionIterator(T element) {
            this.element = element;
        }

        @Override
        public boolean hasNext() {
            return this.element != null;
        }

        @Override
        public T next() {
            T current = this.element;
            if (current == null)
                throw new NoSuchElementException();
            this.element = null;
            return current;
        }

        @Override
        public void remove() {
            if (Option.this.element == null)
                throw new NoSuchElementException();

            clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOption<?> option = (IOption<?>) o;

        return !isEmpty() && !option.isEmpty() ? get().equals(option.get()) : option.isEmpty() && isEmpty();

    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }
}

class LazyOption<T> extends AbstractCollection<T> implements IOption<T> {
    private Either<T, Iterator<T>> object;

    public LazyOption(Iterator<T> iterator) { this.object = Either.right(iterator); }

    @Override
    public final T get() {
        if (this.object != null && this.object.right != null) {
            if (object.right.hasNext())
                this.object = Either.left(object.right.next());
            else
                this.object = Either.left(null);
        }

        return this.object == null ? null : this.object.left;
    }

    @Override
    public final int size() {
        return isEmpty() ? 0 : 1;
    }

    @Override
    public final boolean isEmpty() {
        return get() == null;
    }

    @Override
    public boolean contains(Object o) {
        return !isEmpty() && get().equals(o);
    }

    @Override
    public final IOption<T> or(IOption<T> other) {
        return isEmpty() ? other : this;
    }

    @Override
    public Iterator<T> iterator() {
        return new OptionIterator();
    }

    @Override
    public Object[] toArray() {
        return isEmpty() ? new Object[0] : new Object[] { get() };
    }

    @Override
    public boolean add(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (isEmpty())
            return c.isEmpty();

        if (c.size() == 1)
            return c.iterator().next().equals(get());

        return false;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    private class OptionIterator implements Iterator<T> {
        private T element;
        private boolean terminated;

        @Override
        public boolean hasNext() {
            if (!this.terminated)
                this.element = get();

            return this.element != null && !this.terminated;
        }

        @Override
        public T next() {
            T current = this.element;
            if (current == null)
                throw new NoSuchElementException();
            this.terminated = true;
            return current;
        }

        @Override
        public void remove() {
            if (isEmpty())
                throw new NoSuchElementException();

            clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOption<?> option = (IOption<?>) o;

        return !isEmpty() && !option.isEmpty() ? get().equals(option.get()) : option.isEmpty() && isEmpty();
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

class PureOption<T> extends AbstractCollection<T> implements IOption<T> {
    private Iterable<T> source;

    public PureOption () {}
    public PureOption(Iterable<T> source) {
        this.source = source;
    }

    public final T get() {
        final Iterator<T> i = this.source.iterator();
        return i.hasNext() ? i.next() : null;
    }

    @Override
    public IOption<T> or(IOption<T> other) {
        return isEmpty() ? other : this;
    }

    @Override
    public int size() {
        return get() == null ? 0 : 1;
    }

    @Override
    public boolean isEmpty() {
        return !this.source.iterator().hasNext();
    }

    @Override
    public boolean contains(Object o) {
        final Iterator<T> i = this.source.iterator();
        return i.hasNext() && i.next().equals(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new OptionIterator();
    }

    @Override
    public Object[] toArray() {
        final Iterator<T> i = this.source.iterator();
        return i.hasNext() ? new Object[] { i.next() } : new Object[0];
    }

    @Override
    public boolean add(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        final Iterator<T> i = this.source.iterator();
        if (!i.hasNext())
            return c.isEmpty();

        if (c.size() == 1)
            return c.iterator().next().equals(i.next());

        return false;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    private class OptionIterator implements Iterator<T> {
        private final Iterator<T> iterator;
        private int c;

        public OptionIterator() {
            this.iterator = PureOption.this.source.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext() && c < 1;
        }

        @Override
        public T next() {
            try {
                return this.iterator.next();
            } finally {
                c++;
            }
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOption<?> option = (IOption<?>) o;
        final Iterator<T> i = this.source.iterator();

        if (!i.hasNext() && option.isEmpty()) return true;

        return (i.hasNext() && !option.isEmpty() && i.next().equals(option.get()));
    }

    @Override
    public int hashCode() {
        return 0;
    }
}