/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

import java.util.Iterator;
import java.util.Set;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
final class BulkRemoveOp<T> implements IOctantOperation<T> {
    public final IntAxisAlignedBox range;

    private final Set<OctTree.Entry<T>> removals;

    public BulkRemoveOp(Set<OctTree.Entry<T>> removals) {
        this.removals = removals;
        IntAxisAlignedBox range = null;
        for (OctTree.Entry<T> removal : removals) {
            if (range == null)
                range = removal.key;
            else
                range = range.union(removal.key);
        }
        this.range = range;
    }

    @Override
    public void child(Octant<T> parent, int parity, Vec3i mp, int scale) {
        Octant<T> octant = parent.child(parity);

        if (octant != null) {
            Iterator<OctTree.Entry<T>> iterator = octant.entries().iterator();
            while (iterator.hasNext())
                if (removals.contains(iterator.next()))
                    iterator.remove();

            octant.operation(this, this.range, mp, scale);
        }
    }
}
