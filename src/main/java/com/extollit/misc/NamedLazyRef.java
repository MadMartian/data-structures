package com.extollit.misc;

/**
 * MadMartian Mod
 * <p/>
 * Created by jonathan on 04/02/16.
 * <p/>
 * Copyright (c) 2016 extollIT Enterprises
 * <p/>
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class NamedLazyRef< K, T > extends LazyRef< T > {
    public final K identifier;

    public NamedLazyRef(K identifier) {
        this.identifier = identifier;
    }
    public NamedLazyRef(K identifier, T reference)
    {
        super(reference);
        this.identifier = identifier;
    }

    public T get() throws UnresolvedLazyRefException {
        if (this.reference == null)
            throw new UnresolvedNamedLazyRefException(this);
        return this.reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        NamedLazyRef<?,?> that = (NamedLazyRef<?,?>) o;

        return identifier.equals(that.identifier);
    }

    protected String stringify() { return identifier.toString(); }

    @Override
    public String toString() {
        return stringify() + super.toString();
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
