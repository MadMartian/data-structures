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
public class UnresolvedLazyRefException extends LazyResolutionException {
    public final LazyRef<?> ref;

    public UnresolvedLazyRefException(LazyRef<?> ref)
    {
        this("Lazy reference not resolved", ref);
    }

    protected UnresolvedLazyRefException(String message, LazyRef<?> ref) {
        super(message);
        this.ref = ref;
    }
}
