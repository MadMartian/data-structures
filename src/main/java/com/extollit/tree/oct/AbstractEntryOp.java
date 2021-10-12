/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

/**
 * main
 *
 * Created by jonathan on 22/02/17.
 */
abstract class AbstractEntryOp<T> implements IOctantOperation<T> {
    private final int keyScale;

    protected final IntAxisAlignedBox key;

    protected AbstractEntryOp(IntAxisAlignedBox key) {
        this.key = key;
        this.keyScale = Helper.scale(key);
    }

    @Override
    public final void child(Octant<T> parent, int parity, Vec3i mp, int scale) {
        Octant<T> octant = acquireChild(parent, parity);

        if (octant != null) {
            if (this.keyScale <= scale)
                octant.operation(this, this.key, mp, scale);
            else
                apply(octant);
        }
    }

    protected Octant<T> acquireChild(Octant<T> parent, int parity) {
        return parent.child(parity);
    }

    protected abstract void apply(Octant<T> octant);
}

abstract class AbstractAutoEntryOp<T> extends AbstractEntryOp<T> {
    protected AbstractAutoEntryOp(IntAxisAlignedBox key) {
        super(key);
    }

    @Override
    protected Octant<T> acquireChild(Octant<T> parent, int parity) {
        return parent.autoChild(parity);
    }
}