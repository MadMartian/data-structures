package com.extollit.collect.cache;

import java.lang.ref.SoftReference;
import java.util.Collection;

public final class SoftIterable< V > extends AbstractReferenceIterable<V, SoftReference<V>> {
    public SoftIterable() {}

    public SoftIterable(Collection<V> init) {
        super(init);
    }

    @Override
    protected final SoftReference<V> reference(V value) {
        return new SoftReference<V>(value);
    }
}
