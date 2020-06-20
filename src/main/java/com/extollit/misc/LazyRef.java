package com.extollit.misc;

/**
 * MadMartian Mod
 * <p/>
 * Created by jonathan on 10/01/16.
 * <p/>
 * Copyright (c) 2015 extollIT Enterprises
 * <p/>
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class LazyRef< T > {
    protected T reference;

    public LazyRef() {
        this(null);
    }
    public LazyRef(T ref) {
        this.reference = ref;
    }

    public T replaceWith(T object) throws LazyResolutionException {
        T object0 = this.reference;
        this.reference = object;
        return object0;
    }
    public T resolveWith(T object) throws LazyResolutionException
    {
        if (this.reference != null)
            throw new IllegalStateException("Reference already resolved with: " + this.reference);
        return this.reference = object;
    }

    public T get() throws UnresolvedLazyRefException {
        if (this.reference == null)
            throw new UnresolvedLazyRefException(this);
        return this.reference;
    }

    public final boolean unbound() {
        return this.reference == null;
    }

    protected String stringify() { return ""; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LazyRef)) return false;

        LazyRef<?> lazyRef = (LazyRef<?>) o;

        if (lazyRef.reference == null || this.reference == null)
            return true;

        return this.reference.equals(lazyRef.reference);
    }

    @Override
    public int hashCode() {
        return reference != null ? reference.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (unbound())
            sb.append('?');
        else {
            String value = stringify();
            if (!value.isEmpty()) {
                sb.append(" (");
                sb.append(value);
                sb.append(')');
            }
        }
        return sb.toString();
    }
}
