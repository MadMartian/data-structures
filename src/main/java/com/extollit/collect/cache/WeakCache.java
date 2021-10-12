package com.extollit.collect.cache;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 09/02/16.
 *
 * Copyright (c) 2016 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class WeakCache < K, V > extends AbstractCache< K, V, WeakReference<V> > implements ICache< K, V > {

    public WeakCache() {
        this(new HashMap<K, WeakReference<V>>());
    }
    public WeakCache(int initialCapacity) {
        this(new HashMap<K, WeakReference<V>>(initialCapacity));
    }
    private WeakCache(Map<K, WeakReference<V>> delegate) {
        super(delegate);
    }

    @Override
    protected WeakReference<V> reference(V value) {
        return new WeakReference<V>(value);
    }
}
