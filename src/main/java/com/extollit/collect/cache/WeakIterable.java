package com.extollit.collect.cache;

import java.lang.ref.WeakReference;
import java.util.Collection;

public final class WeakIterable< V > extends AbstractReferenceIterable<V, WeakReference<V>> {
    public WeakIterable() {}

    public WeakIterable(Collection<V> init) {
        super(init);
    }

    @Override
    protected final WeakReference<V> reference(V value) {
        return new WeakReference<V>(value);
    }
}
