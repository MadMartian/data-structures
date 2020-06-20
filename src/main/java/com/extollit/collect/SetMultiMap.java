/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.collect;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * main
 * <p/>
 * Created by jonathan on 11/10/16.
 */
public class SetMultiMap< K, V > extends MultiMap<K, V, Set<V>> {
    public SetMultiMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SetMultiMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SetMultiMap() {
    }

    public SetMultiMap(Map<? extends K, ? extends Set<V>> t) {
        super(t);
    }

    @Override
    protected Set<V> createCollection(K key) {
        return new HashSet<V>();
    }
}
