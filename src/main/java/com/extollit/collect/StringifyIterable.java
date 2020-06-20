package com.extollit.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class StringifyIterable< T > implements Iterable< T > {
    public final Iterable<T> delegate;

    private final boolean propagate;

    public StringifyIterable(Iterable<T> delegate) {
        this(false, delegate);
    }

    public StringifyIterable(boolean propagate, Iterable<T> delegate) {
        this.delegate = delegate;
        this.propagate = propagate;
    }

    private final class StringifyIter implements Iterator< T > {
        private final Iterator<T> delegate;

        public StringifyIter(Iterator<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @SuppressWarnings({"unchecked", "ConstantConditions"})
        @Override
        public T next() {
            T next = this.delegate.next();
            if (next instanceof Iterable<?> && !(next instanceof StringifyIterable<?>))
                return (T)new StringifyIterable<Object>((Iterable<Object>) next);
            else if (next != null && next.getClass().isArray())
                return (T)new StringifyIterable<Object>(Arrays.asList((Object[])next));
            else
                return next;
        }
    }

    @Override
    public Iterator<T> iterator() {
        if (this.propagate)
            return new StringifyIter(this.delegate.iterator());
        else
            return this.delegate.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.delegate.forEach(action);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;
        for (T element : this)
        {
            if (!first)
                sb.append(", ");
            else
                first = false;

            sb.append(element);
        }
        sb.append(']');
        return sb.toString();
    }
}
