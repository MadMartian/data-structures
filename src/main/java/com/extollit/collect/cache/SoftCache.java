package com.extollit.collect.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 17/02/16.
 *
 * Copyright (c) 2016 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class SoftCache<K, V> extends AbstractCache<K, V, SoftReference<V>> implements ICache<K, V> {
    public SoftCache() {
        this(new HashMap<K, SoftReference<V>>());
    }
    public SoftCache(int initialCapacity) {
        this(new HashMap<K, SoftReference<V>>(initialCapacity));
    }
    private SoftCache(Map<K, SoftReference<V>> delegate) {
        super(delegate);
    }

    @Override
    protected SoftReference<V> reference(V value) {
        return new SoftReference<V>(value);
    }
}
