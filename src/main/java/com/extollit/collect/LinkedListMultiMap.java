package com.extollit.collect;

import java.util.LinkedList;
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
public class LinkedListMultiMap<K, V> extends MultiMap<K, V, LinkedList<V>> {
    public LinkedListMultiMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public LinkedListMultiMap(int initialCapacity) {
        super(initialCapacity);
    }

    public LinkedListMultiMap() {
    }

    public LinkedListMultiMap(Map<? extends K, ? extends LinkedList<V>> t) {
        super(t);
    }

    @Override
    protected final LinkedList<V> createCollection(K key) {
        return new LinkedList<V>();
    }
}
