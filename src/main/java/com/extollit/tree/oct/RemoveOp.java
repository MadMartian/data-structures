/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import java.util.Iterator;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
final class RemoveOp<T> extends AbstractEntryOp<T> {
    private final OctTree.Entry<T> entry;

    public RemoveOp(OctTree.Entry<T> entry) {
        super(entry.key);
        this.entry = entry;
    }

    @Override
    protected void apply(Octant<T> octant) {
        Iterator<OctTree.Entry<T>> iterator = octant.entries().iterator();
        while (iterator.hasNext()) {
            OctTree.Entry<T> entry = iterator.next();
            if (entry.equals(this.entry))
                iterator.remove();
        }
    }
}
