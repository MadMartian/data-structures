package com.extollit.collect.cache;

import com.extollit.collect.XFormIterable;

import java.lang.ref.Reference;
import java.util.*;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 17/02/16.
 *
 * Copyright (c) 2016 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
abstract class AbstractCache<K, V, R extends Reference<V>> implements ICache<K, V> {
    private static final class Purge extends AbstractPurgeThread< AbstractCache< ?, ?, ? >> {
        public Purge() {
            super("extollIT Cache - Purge");
        }

        @Override
        protected int cull(int offset, AbstractCache<?, ?, ?> cache) {
            return cache.cull(offset, 100);
        }
    }

    private static final Purge PURGE_THREAD = new Purge();

    static {
        PURGE_THREAD.start();
    }

    protected final Map<K, R> delegate;

    protected AbstractCache(Map<K, R> delegate) {
        this.delegate = delegate;
        PURGE_THREAD.register(this);
    }

    private synchronized int cull(int offset, int batch) {
        final Set<Map.Entry<K, R>> entries = this.delegate.entrySet();
        final Iterator<? extends Map.Entry<K, ? extends Reference<? extends V>>> i = entries.iterator();
        {
            final int entriesSize = entries.size();
            if (entriesSize > 0)
                offset = offset % entriesSize;
        }

        int fin = offset;

        while (offset-- > 0)
            i.next();

        while (i.hasNext() && batch-- > 0) {
            Reference<? extends V> ref = i.next().getValue();
            if (ref.get() == null)
                i.remove();

            ++fin;
        }
        return batch > 0 ? 0 : fin;
    }

    @Override
    public final synchronized V replace(K key, V value) {
        R old = this.delegate.put(key, reference(value));
        return old != null ? old.get() : null;
    }

    @Override
    public final synchronized V query(K key) {
        final Map<K, R> map = this.delegate;

        if (map.containsKey(key)) {
            Reference<? extends V> ref = map.get(key);
            return ref.get();
        }

        return null;
    }

    protected abstract R reference(V value);

    @Override
    public final synchronized void purge() {
        this.delegate.clear();
    }

    @Override
    public final synchronized V remove(K key) {
        R old = this.delegate.remove(key);
        return old == null ? null : old.get();
    }

    @Override
    public synchronized V acquire(K key, IFactory<K, V> factory) {
        V value = null;

        final Map<K, R> map = this.delegate;

        if (map.containsKey(key)) {
            Reference<? extends V> ref = map.get(key);
            value = ref.get();
        }
        if (value == null) {
            try {
                map.put(key, reference(value = factory.create(key)));
            } catch (InstantiationException e) {
                return null;
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        return value;
    }

    @Override
    public final synchronized Set<K> keys() {
        return new HashSet<K>(this.delegate.keySet());
    }

    private final class MapEntry implements Map.Entry<K, V> {
        public final Map.Entry<K, R> delegate;

        public MapEntry(Map.Entry<K, R> delegate) {
            this.delegate = delegate;
        }

        @Override
        public final K getKey() {
            return delegate.getKey();
        }

        @Override
        public final V getValue() {
            return delegate.getValue().get();
        }

        @Override
        public V setValue(V value) {
            R result = delegate.setValue(reference(value));
            return result == null ? null : result.get();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry<?, ?>)) return false;

            Map.Entry<?, ?> other = (Map.Entry<?, ?>) o;

            final V thisValue = getValue();
            Object otherValue = other.getValue();

            if (!getKey().equals(other.getKey())) return false;
            if (thisValue != null && !thisValue.equals(otherValue)) return false;

            return thisValue == null && otherValue == null;
        }

        @Override
        public int hashCode() {
            return getKey().hashCode();
        }
    }

    @Override
    public synchronized Iterator<Map.Entry<K, V>> iterator() {
        List<Map.Entry<K, R>> snapshot = new ArrayList<Map.Entry<K, R>>(delegate.entrySet());
        return new XFormIterable.Iter<Map.Entry<K, V>, Map.Entry<K, R>>(snapshot.iterator()) {
            @Override
            public Map.Entry<K, V> transform(Map.Entry<K, R> element) {
                return new MapEntry(element);
            }
        };
    }
}
