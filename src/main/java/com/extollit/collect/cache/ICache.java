package com.extollit.collect.cache;

import java.util.Map;
import java.util.Set;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 17/02/16.
 *
 * Copyright (c) 2016 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public interface ICache<K, V> extends Iterable<Map.Entry<K, V>> {
    void purge();

    V acquire(K key, IFactory<K, V> factory);
    V query(K key);
    V replace(K key, V value);
    V remove(K key);

    Set<K> keys();

    interface IFactory<K, V> {
        V create(K key) throws InstantiationException;
    }
}
