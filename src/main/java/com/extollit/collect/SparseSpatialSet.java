package com.extollit.collect;

import com.extollit.linalg.immutable.Vec3i;

import java.util.*;

public class SparseSpatialSet implements Set<Vec3i> {
    private int size;

    private final Map<Integer, Map<Integer, Set<Integer>>> space = new HashMap<Integer, Map<Integer, Set<Integer>>>();

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object key) {
        if (key == null)
            throw new NullPointerException();

        final Map<Integer, Map<Integer, Set<Integer>>> z = this.space;
        final Vec3i coords = (Vec3i) key;
        final Map<Integer, Set<Integer>> y = z.get(coords.z);
        if (y != null) {
            final Set<Integer> x = y.get(coords.y);
            return x != null && x.contains(coords.x);
        }
        return false;
    }

    private final class CoordsIterator extends FilterIterable.Iter<Vec3i> implements Iterator<Vec3i> {
        private Iterator<Map.Entry<Integer, Map<Integer, Set<Integer>>>> zi;
        private Iterator<Map.Entry<Integer, Set<Integer>>> yi;
        private Iterator<Integer> xi;

        private int z, y;

        public CoordsIterator() {
            this.zi = SparseSpatialSet.this.space.entrySet().iterator();
        }

        @Override
        protected Vec3i findNext() {
            final Iterator<Map.Entry<Integer, Map<Integer, Set<Integer>>>> zi = this.zi;
            Iterator<Integer> xi = this.xi;
            Iterator<Map.Entry<Integer, Set<Integer>>> yi = this.yi;

            while (xi == null || !xi.hasNext()) {
                while (yi == null || !yi.hasNext()) {
                    if (zi.hasNext()) {
                        final Map.Entry<Integer, Map<Integer, Set<Integer>>> entry = zi.next();
                        this.z = entry.getKey();
                        yi = this.yi = entry.getValue().entrySet().iterator();
                    } else
                        return null;
                }
                final Map.Entry<Integer, Set<Integer>> entry = yi.next();
                this.y = entry.getKey();
                xi = this.xi = entry.getValue().iterator();
            }

            final int x = xi.next();
            return new Vec3i(x, this.y, this.z);
        }
    }

    @Override
    public Iterator<Vec3i> iterator() {
        return new CoordsIterator();
    }

    @Override
    public Object[] toArray() {
        return toArray(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] array) {
        return (T[]) toArrayImpl((Vec3i[]) array);
    }

    private Vec3i[] toArrayImpl(Vec3i[] array) {
        if (array == null || array.length < this.size)
            array = new Vec3i[this.size];

        int c = 0;
        for (Vec3i coord : this)
            array[c++] = coord;
        return array;
    }

    @Override
    public boolean add(Vec3i key) {
        if (key == null)
            throw new NullPointerException();

        final Map<Integer, Map<Integer, Set<Integer>>> z = this.space;
        final Map<Integer, Set<Integer>> y;
        final Set<Integer> x;

        if (!z.containsKey(key.z)) {
            z.put(key.z, y = new HashMap<Integer, Set<Integer>>());
            y.put(key.y, x = new HashSet<Integer>());
        } else {
            y = z.get(key.z);
            if (y.containsKey(key.y))
                x = y.get(key.y);
            else
                y.put(key.y, x = new HashSet<Integer>());
        }

        final boolean yes = x.add(key.x);
        if (yes)
            this.size++;

        return yes;
    }

    @Override
    public boolean remove(Object key) {
        if (key == null)
            throw new NullPointerException();

        final Map<Integer, Map<Integer, Set<Integer>>> z = this.space;
        final Vec3i coords = (Vec3i) key;
        final Map<Integer, Set<Integer>> y = z.get(coords.z);
        if (y != null) try {
            final Set<Integer> x = y.get(coords.y);
            if (x != null) try {
                final boolean yes = x.remove(coords.x);
                if (yes)
                    this.size--;
                return yes;
            } finally {
                if (x.isEmpty())
                    y.remove(coords.y);
            }
        } finally {
            if (y.isEmpty())
                z.remove(coords.z);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            boolean result = false;
            for (Vec3i key : this)
                if (key.equals(o)) {
                    result = true;
                    break;
                }

            if (!result)
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Vec3i> c) {
        boolean added = false;
        for (Vec3i key : c)
            added |= add(key);

        return added;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        final List<Vec3i> removals = new LinkedList<Vec3i>();
        for (Vec3i key : this)
            if (!c.contains(key))
                removals.add(key);

        return removeAll(removals);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        for (Object o : c)
            flag |= remove(o);

        return flag;
    }

    @Override
    public void clear() {
        this.space.clear();
        this.size = 0;
    }
}
