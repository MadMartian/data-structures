package com.extollit.misc;

import java.text.MessageFormat;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 10/10/15.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class UnresolvedNamedLazyRefException extends UnresolvedLazyRefException {
    public final NamedLazyRef<?,?> ref;

    public UnresolvedNamedLazyRefException(NamedLazyRef<?, ?> ref)
    {
        super(
            MessageFormat.format(
                "Lazy reference ''{0}'' not resolved",
                ref.identifier
            ),
            ref
        );
        this.ref = ref;
    }
}
