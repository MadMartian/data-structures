/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * main
 *
 * Created by jonathan on 24/12/16.
 */
public class UniqueIterable<E> extends FilterIterable<E> {
    public UniqueIterable(Iterable<E> delegate) {
        super(delegate);
    }

    protected class UniqueIterImpl extends IterImpl {
        private final Set<E> visited = new HashSet<E>();

        private UniqueIterImpl(Iterator<E> delegate) {
            super(delegate);
        }

        @Override
        protected boolean shouldHave(E next) {
            return !visited.contains(next);
        }

        @Override
        protected E findNext() {
            E result = super.findNext();
            if (result != null)
                visited.add(result);
            return result;
        }
    }

    @Override
    protected boolean shouldHave(E element) {
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new UniqueIterImpl(super.delegate.iterator());
    }
}
