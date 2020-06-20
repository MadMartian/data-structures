package com.extollit.collect;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AdapterCollection< A, B > extends AbstractCollection< A > implements ITypeMappable< A, B > {
    private final Collection< B > delegate;

    public AdapterCollection(Collection<B> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Iterator<A> iterator() {
        return new AdapterIterable.AdapterIterator<A, B>(this.delegate.iterator()) {
            @Override
            protected A map(B input) {
                return AdapterCollection.this.map(input);
            }
        };
    }

    @Override
    public int size() {
        return this.delegate.size();
    }
}
