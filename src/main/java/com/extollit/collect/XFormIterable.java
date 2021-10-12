package com.extollit.collect;

import java.util.Arrays;
import java.util.Iterator;

/**
 * MadMartian Mod
 *
 * Created by jonathann on 15-09-25.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public abstract class XFormIterable< Out, In > implements Iterable< Out >, IXFormer< Out, In >, IFilter< In > {
    private final Iterable<? extends In> delegate;

    public XFormIterable(In... entries)
    {
        this(Arrays.asList(entries));
    }
    public XFormIterable(Iterable<? extends In> delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public Iterator<Out> iterator() {
        return new IterImpl(this.delegate.iterator());
    }

    @Override
    public boolean test(In element) { return true; }
    @Override
    public Out transform(In element) { return (Out)element; }

    private class IterImpl extends Iter<Out, In> {
        public IterImpl(Iterator<? extends In> iterator) {
            super(iterator);
        }

        @Override
        public Out transform(In element) {
            return XFormIterable.this.transform(element);
        }

        @Override
        public boolean test(In element) {
            return XFormIterable.this.test(element);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Out value : this) {
            sb.append(value);
            sb.append(", ");
        }
        if (sb.length() > 1)
            sb.delete(sb.length() - 2, sb.length());
        sb.append(']');
        return sb.toString();
    }

    public static class Iter< Out, In > implements IXFormer< Out, In >, IFilter< In >, Iterator<Out> {
        protected final Iterator<? extends In> delegate;
        private In element;

        public Iter(Iterator<? extends In> iterator) {
            this.delegate = iterator;
        }

        @Override
        public boolean test(In element) { return true; }
        @Override
        public Out transform(In element) { return (Out)element; }

        private In advance()
        {
            In element = null;

            while (this.delegate.hasNext()) {
                element = this.delegate.next();
                if (test(element))
                    break;
                else
                    element = null;
            }

            return element;
        }

        @Override
        public final boolean hasNext() {
            if (this.element == null)
                this.element = advance();

            return this.element != null;
        }

        @Override
        public final Out next() {
            if (this.element == null)
                this.element = advance();

            In current = this.element;
            this.element = advance();
            return transform(current);
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
