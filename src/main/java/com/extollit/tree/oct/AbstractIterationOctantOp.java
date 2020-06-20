/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

import java.util.*;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
abstract class AbstractIterationOctantOp<T> implements IOctantOperation<T>,IIterationOctantOperation<T> {
    private final List<OctTree.Entry<T>> entryQueue = new ArrayList<OctTree.Entry<T>>();
    private final Deque<OctantTraversalRegister<T>> octantQueue = new LinkedList<OctantTraversalRegister<T>>();

    @Override
    public final void child(Octant<T> parent, int parity, Vec3i mp, int scale) {
        Octant<T> octant = parent.child(parity);

        if (octant != null) {
            propagate(octant, entryQueue);
            octantQueue.add(new OctantTraversalRegister<T>(this, octant, mp, scale));
        }
    }

    protected abstract void propagate(Octant<T> octant, List<OctTree.Entry<T>> entryQueue);
    protected abstract void traverse(OctantTraversalRegister<T> next);

    @Override
    public final List<OctTree.Entry<T>> next() {
        while (this.entryQueue.isEmpty() && !this.octantQueue.isEmpty()) {
            OctantTraversalRegister<T> next = this.octantQueue.pop();
            traverse(next);
        }

        if (!this.entryQueue.isEmpty()) {
            List<OctTree.Entry<T>> result = new ArrayList<OctTree.Entry<T>>(this.entryQueue);
            this.entryQueue.clear();
            return result;
        } else
            return Collections.emptyList();
    }
}

class PointIterationOctantOp<T> extends AbstractIterationOctantOp<T> {
    public final com.extollit.linalg.immutable.Vec3i key;

    public PointIterationOctantOp(com.extollit.linalg.immutable.Vec3i key) {
        this.key = key;
    }

    @Override
    protected void propagate(Octant<T> octant, List<OctTree.Entry<T>> entryQueue) {
        for (OctTree.Entry<T> entry : octant.entries()) {
            if (entry.key.contains(this.key))
                entryQueue.add(entry);
        }
    }

    @Override
    protected void traverse(OctantTraversalRegister<T> next) {
        next.apply(key);
    }
}

abstract class AbstractBoundedIterationOctantOp<T> extends AbstractIterationOctantOp<T> {
    private final Set<OctTree.Entry<T>> visited = new HashSet<OctTree.Entry<T>>();

    @Override
    protected final void propagate(Octant<T> octant, List<OctTree.Entry<T>> entryQueue) {
        for (OctTree.Entry<T> entry : octant.entries()) {
            if (!visited.contains(entry) && test(entry)) {
                entryQueue.add(entry);
                visited.add(entry);
            }
        }
    }

    protected abstract boolean test(OctTree.Entry<T> entry);
}

class BoxIterationOctantOp<T> extends AbstractBoundedIterationOctantOp<T> {
    public final IntAxisAlignedBox key;

    public BoxIterationOctantOp(IntAxisAlignedBox key) {
        this.key = key;
    }

    @Override
    protected boolean test(OctTree.Entry<T> entry) {
        return entry.key.intersects(this.key);
    }

    @Override
    protected void traverse(OctantTraversalRegister<T> next) {
        next.apply(key);
    }
}

class AllIterationOctantOp<T> extends AbstractBoundedIterationOctantOp<T> {
    @Override
    protected boolean test(OctTree.Entry<T> entry) {
        return true;
    }

    @Override
    protected void traverse(OctantTraversalRegister<T> next) {
        next.apply();
    }
}