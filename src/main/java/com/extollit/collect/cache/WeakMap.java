package com.extollit.collect.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakMap< K, V > implements ICache<K, V> {
    private final Map< K, V > delegate = new WeakHashMap<K, V>();

    @Override
    public void purge() {
        this.delegate.clear();
    }

    @Override
    public synchronized V acquire(K key, IFactory<K, V> factory) {
        final Map<K, V> map = this.delegate;
        V value = map.get(key);
        if (value == null) {
            try {
                map.put(key, value = factory.create(key));
            } catch (InstantiationException e) {
                return null;
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        return value;
    }

    @Override
    public V query(K key) {
        return this.delegate.get(key);
    }

    @Override
    public V replace(K key, V value) {
        return this.delegate.put(key, value);
    }

    @Override
    public V remove(K key) {
        return this.delegate.remove(key);
    }

    @Override
    public Set<K> keys() {
        return this.delegate.keySet();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return this.delegate.entrySet().iterator();
    }
}
