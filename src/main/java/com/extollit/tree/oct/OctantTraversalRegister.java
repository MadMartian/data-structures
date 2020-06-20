/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
final class OctantTraversalRegister<T> {
    public final Octant<T> octant;
    public final Vec3i mp;
    public final int scale;

    private final IOctantOperation<T> operation;

    OctantTraversalRegister(IOctantOperation<T> operation, Octant<T> octant, Vec3i mp, int scale) {
        this.octant = octant;
        this.mp = new Vec3i(mp);
        this.scale = scale;
        this.operation = operation;
    }

    public void apply(IntAxisAlignedBox key) {
        octant.operation(operation, key, mp, scale);
    }

    public void apply(com.extollit.linalg.immutable.Vec3i key) {
        octant.operation(operation, key, mp, scale);
    }

    public void apply() {
        octant.operation(operation, mp, scale);
    }
}
