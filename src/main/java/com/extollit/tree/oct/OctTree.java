/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * main
 * <p/>
 * Created by jonathan on 28/11/16.
 */
public class OctTree< T > {
    public static final class Entry<T> {
        public final IntAxisAlignedBox key;
        public final T value;

        public Entry(IntAxisAlignedBox key, T value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?> entry = (Entry<?>) o;

            if (!key.equals(entry.key)) return false;
            return value.equals(entry.value);

        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return MessageFormat.format("{0} -> {1}", this.key, this.value);
        }
    }

    private final Octant<T> root;
    private final Set<Entry<T>> remq = new HashSet<Entry<T>>();

    public OctTree() {
        this.root = new Octant<T>();
    }

    private abstract class BaseIter implements Iterator<Entry<T>> {
        private final IIterationOctantOperation<T> op;

        private Iterator<Entry<T>> sub;

        protected Entry<T> current, last;

        protected BaseIter(IIterationOctantOperation<T> op) {
            this.op = op;
        }

        protected Entry<T> find() {
            if (sub == null)
                start(OctTree.this.root, op);

            if (sub == null || !sub.hasNext())
                sub = op.next().iterator();

            if (sub.hasNext())
                return current = sub.next();
            else
                return current = null;
        }

        protected abstract void start(Octant<T> root, IIterationOctantOperation<T> op);

        @Override
        public final boolean hasNext() {
            if (this.current == null)
                find();

            return this.current != null;
        }

        @Override
        public final Entry<T> next() {
            if (this.current == null)
                if (find() == null)
                    throw new NoSuchElementException();

            Entry<T> next = this.current;
            find();
            return this.last = next;
        }

        @Override
        public final void remove() {
            if (this.last == null)
                throw new NoSuchElementException();

            OctTree.this.remq.add(this.last);
        }
    }

    private class PointIter extends BaseIter implements Iterator<Entry<T>> {
        private final com.extollit.linalg.immutable.Vec3i query;

        public PointIter(com.extollit.linalg.immutable.Vec3i query) {
            super(new PointIterationOctantOp<T>(query));
            this.query = query;
        }

        @Override
        protected void start(Octant<T> root, IIterationOctantOperation<T> op) {
            root.operation(op, this.query, new Vec3i(0, 0, 0), Helper.TOP_SCALE);
        }
    }

    private class AllIter extends BaseIter implements Iterator<Entry<T>> {
        protected AllIter() {
            super(new AllIterationOctantOp<T>());
        }

        @Override
        protected void start(Octant<T> root, IIterationOctantOperation<T> op) {
            root.operation(op, new Vec3i(0, 0, 0), Helper.TOP_SCALE);
        }
    }

    private class BoxIter extends BaseIter implements Iterator<Entry<T>> {
        private final IntAxisAlignedBox query;

        public BoxIter(IntAxisAlignedBox query) {
            super(new BoxIterationOctantOp<T>(query));
            this.query = query;
        }

        @Override
        protected void start(Octant<T> root, IIterationOctantOperation<T> op) {
            root.operation(op, this.query, new Vec3i(0, 0, 0), Helper.TOP_SCALE);
        }
    }

    public void add(IntAxisAlignedBox key, T value) {
        flushRemovals();
        IOctantOperation<T> addOp = new AddEntryOctantOp<T>(new Entry<T>(key, value));
        this.root.operation(addOp, key, new Vec3i(0, 0, 0), Helper.TOP_SCALE);
    }

    public void remove(IntAxisAlignedBox key, T value) {
        flushRemovals();
        remove(new Entry<T>(key, value));
    }

    private void remove(Entry<T> entry) {
        flushRemovals();
        IOctantOperation<T> remOp = new RemoveOp<T>(entry);
        this.root.operation(remOp, entry.key, new Vec3i(0, 0, 0), Helper.TOP_SCALE);
    }

    public Iterator<Entry<T>> findAll(com.extollit.linalg.immutable.Vec3i point) {
        flushRemovals();
        return new PointIter(point);
    }
    public Iterator<Entry<T>> findAll(IntAxisAlignedBox box) {
        flushRemovals();
        return new BoxIter(box);
    }
    public Iterator<Entry<T>> findAll() {
        flushRemovals();
        return new AllIter();
    }

    public void flushRemovals() {
        if (!remq.isEmpty()) {
            BulkRemoveOp<T> remOp = new BulkRemoveOp<T>(remq);
            this.root.operation(remOp, remOp.range, new Vec3i(0, 0, 0), Helper.TOP_SCALE);
            remq.clear();
        }
    }
}
