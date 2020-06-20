/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * main
 * <p/>
 * Created by jonathan on 25/12/16.
 */
@SuppressWarnings("PointlessBitwiseExpression")
final class Octant< T > {
    private Octant<T> [] children;
    private List<OctTree.Entry<T>> entries;

    Octant() {}

    public void operation(IOctantOperation<T> op, com.extollit.linalg.immutable.Vec3i key, Vec3i mp, int scale) {
        byte childIndex = Helper.parity(key, mp);
        operation(op, childIndex, mp, scale);
    }

    public void operation(IOctantOperation<T> op, Vec3i mp, int scale) {
        operation(op, (byte)0, mp, scale);
        operation(op, (byte)1, mp, scale);
        operation(op, (byte)2, mp, scale);
        operation(op, (byte)3, mp, scale);
        operation(op, (byte)4, mp, scale);
        operation(op, (byte)5, mp, scale);
        operation(op, (byte)6, mp, scale);
        operation(op, (byte)7, mp, scale);
    }

    public void operation(IOctantOperation<T> op, IntAxisAlignedBox key, Vec3i mp, int scale) {
        final Vec3i p = new Vec3i(key.min);
        final boolean [] flagged = new boolean[8];

        flagged[Helper.parity(p, mp)] = true;
        p.x = key.max.x;
        flagged[Helper.parity(p, mp)] = true;
        p.x = key.min.x;
        p.y = key.max.y;
        flagged[Helper.parity(p, mp)] = true;
        p.x = key.max.x;
        flagged[Helper.parity(p, mp)] = true;
        p.x = key.min.x;
        p.y = key.min.y;
        p.z = key.max.z;
        flagged[Helper.parity(p, mp)] = true;
        p.x = key.max.x;
        flagged[Helper.parity(p, mp)] = true;
        p.x = key.min.x;
        p.y = key.max.y;
        flagged[Helper.parity(p, mp)] = true;
        p.x = key.max.x;
        flagged[Helper.parity(p, mp)] = true;

        if (flagged[0])
            operation(op, (byte)0, mp, scale);
        if (flagged[1])
            operation(op, (byte)1, mp, scale);
        if (flagged[2])
            operation(op, (byte)2, mp, scale);
        if (flagged[3])
            operation(op, (byte)3, mp, scale);
        if (flagged[4])
            operation(op, (byte)4, mp, scale);
        if (flagged[5])
            operation(op, (byte)5, mp, scale);
        if (flagged[6])
            operation(op, (byte)6, mp, scale);
        if (flagged[7])
            operation(op, (byte)7, mp, scale);
    }

    private void operation(IOctantOperation<T> operation, byte parity, Vec3i mp, int scale) {
        final int halfScale = scale >> 1;
        final int offs[] = { -halfScale, +halfScale };
        final int
                i = (parity >> 0) & 1,
                j = (parity >> 1) & 1,
                k = (parity >> 2) & 1;

        mp.x += offs[i];
        mp.y += offs[j];
        mp.z += offs[k];

        operation.child(this, parity, mp, halfScale);

        mp.x -= offs[i];
        mp.y -= offs[j];
        mp.z -= offs[k];
    }

    @SuppressWarnings("unchecked")
    public Octant<T> autoChild(int parity) {
        if (children == null)
            children = new Octant[8];
        if (children[parity] == null)
            return children[parity] = new Octant<T>();
        else
            return children[parity];
    }

    public Octant<T> child(int parity) {
        return children == null ? null : children[parity] == null ? null : children[parity];
    }

    public void add(OctTree.Entry<T> entry) {
        if (entries == null)
            entries = new LinkedList<OctTree.Entry<T>>();
        entries.add(entry);
    }

    public Iterable<OctTree.Entry<T>> entries() {
        return entries == null ? Collections.<OctTree.Entry<T>>emptyList() : entries;
    }
}