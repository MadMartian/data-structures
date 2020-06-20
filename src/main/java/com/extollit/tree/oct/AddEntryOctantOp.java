/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
final class AddEntryOctantOp<T> extends AbstractAutoEntryOp<T> {
    public final OctTree.Entry<T> entry;

    public AddEntryOctantOp(OctTree.Entry<T> entry) {
        super(entry.key);
        this.entry = entry;
    }

    @Override
    protected void apply(Octant<T> octant) {
        octant.add(this.entry);
    }
}
