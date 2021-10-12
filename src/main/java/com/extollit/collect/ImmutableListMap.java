package com.extollit.collect;

import java.util.*;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 03/11/15.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public abstract class ImmutableListMap<K, V> implements Iterable<V> {
    private final List<V> list;
    private final Map<K, V> map;

    public ImmutableListMap(List<? extends V> delegate) {
        this.list = Collections.unmodifiableList(new ArrayList<V>(delegate));
        this.map = new LinkedHashMap<K, V>(delegate.size());
        for (V value : delegate)
            this.map.put(keyFor(value), value);
    }

    protected abstract K keyFor(V value);

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<V> iterator() {
        return list.iterator();
    }

    public V get(K key) { return this.map.get(key); }

    @Override
    public boolean equals(Object o) {
        if (o instanceof List) return this.list.equals(o);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableListMap<?, ?> that = (ImmutableListMap<?, ?>) o;

        return list.equals(that.list);

    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public boolean has(K name) {
        return this.map.containsKey(name);
    }
}
