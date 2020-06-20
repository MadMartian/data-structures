package com.extollit.collect;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * MadMartian Mod
 * <p/>
 * Created by jonathann on 15-10-23.
 * <p/>
 * Copyright (c) 2015 extollIT Enterprises
 * <p/>
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public abstract class MultiMap<K, E, C extends Collection<E>> extends Hashtable<K, C> {
    public MultiMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MultiMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MultiMap() {
    }

    public MultiMap(Map<? extends K, ? extends C> t) {
        super(t);
    }

    public final void add(K key, E value) {
        collectionFor(key).add(value);
    }

    public C collectionFor(K key)
    {
        if (containsKey(key))
            return get(key);
        else {
            C list = createCollection(key);
            put(key, list);
            return list;
        }
    }

    protected abstract C createCollection(K key);
}
