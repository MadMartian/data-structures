/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.util.Collection;
import java.util.LinkedList;

/**
 * main
 * <p/>
 * Created by jonathan on 16/10/16.
 */
public abstract class GroupedCollection< K, E, Out > extends MultiMap< K, Out, Collection< Out > > {
    public void add(Iterable<E> from)
    {
        for (E element : from)
            super.add(keyFor(element), transform(element));
    }

    @Override
    protected final Collection<Out> createCollection(K key) {
        return new LinkedList<Out>();
    }

    protected abstract K keyFor(E element);
    protected abstract Out transform(E element);
}
