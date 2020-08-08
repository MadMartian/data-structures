package com.extollit.collect;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.immutable.Vec3i;

import java.util.*;

public class SparseSpatialMap<T> implements Map<Vec3i, T> {
    private static final float INNER_LOAD_FACTOR = 0.75f, OUTER_LOAD_FACTOR = 0.9f;

    private static final class GreaterCoarseKey {
        public final byte x, y, z;

        private final int hashCode;

        private GreaterCoarseKey(int x, int y, int z) {
            this.x = (byte)(x & 0xFF);
            this.y = (byte)(y & 0xFF);
            this.z = (byte)(z & 0xFF);

            int result = 1;
            result = 31 * result + z;
            result = 31 * result + y;
            result = 31 * result + x;
            this.hashCode = result;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GreaterCoarseKey greaterCoarseKey = (GreaterCoarseKey) o;
            return x == greaterCoarseKey.x &&
                    y == greaterCoarseKey.y &&
                    z == greaterCoarseKey.z;
        }

        @Override
        public final int hashCode() { return this.hashCode; }
    }

    private static final class LesserCoarseKey {
        public final int x, y, z;
        private final int hashCode;

        private LesserCoarseKey(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;

            int result = 1;
            result = 31 * result + z;
            result = 31 * result + y;
            result = 31 * result + x;
            this.hashCode = result;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LesserCoarseKey lesserCoarseKey = (LesserCoarseKey) o;
            return x == lesserCoarseKey.x &&
                    y == lesserCoarseKey.y &&
                    z == lesserCoarseKey.z;
        }

        @Override
        public final int hashCode() { return this.hashCode; }
    }

    private final int order, mask;

    private int size;
    private Vec3i key0;
    private Map<GreaterCoarseKey, T> inner0;

    private final Map<LesserCoarseKey, Map<GreaterCoarseKey, T>> space;

    public SparseSpatialMap(int order) {
        this.order = order;
        this.mask = (2 << order) - 1;
        this.space = new HashMap<LesserCoarseKey, Map<GreaterCoarseKey, T>>((1 << this.order) >> 2, OUTER_LOAD_FACTOR);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean containsKey(Vec3i coords) {
        final Map<GreaterCoarseKey, T> inner = acquireInner(coords);
        if (inner != null) {
            final GreaterCoarseKey greaterKey = greaterKey(coords);
            return inner.containsKey(greaterKey);
        }
        return false;
    }

    @Override
    public boolean containsKey(Object coords) {
        return containsKey((Vec3i)coords);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            throw new NullPointerException();

        for (Map<GreaterCoarseKey, T> inner : this.space.values())
            if (inner.containsValue(value))
                return true;

        return false;
    }

    public T get(Vec3i coords) {
        final Map<GreaterCoarseKey, T> inner = acquireInner(coords);
        if (inner != null) {
            final GreaterCoarseKey greaterKey = greaterKey(coords);
            return inner.get(greaterKey);
        }
        return null;
    }
    @Override
    public T get(Object coords) {
        return get((Vec3i)coords);
    }

    @Override
    public T put(Vec3i coords, T value) {
        if (value == null || coords == null)
            throw new NullPointerException();

        final LesserCoarseKey lesserKey = lesserKey(this.key0 = coords);
        Map<GreaterCoarseKey, T> inner = this.inner0 = this.space.get(lesserKey);
        if (inner == null)
            this.space.put(lesserKey, inner = this.inner0 = new HashMap<GreaterCoarseKey, T>(this.order << 2, INNER_LOAD_FACTOR));

        final T value0 = inner.put(greaterKey(coords), value);
        if (value0 == null)
            this.size++;

        return value0;
    }

    public T remove(Vec3i coords) {
        if (coords == null)
            throw new NullPointerException();

        final LesserCoarseKey lesserKey = lesserKey(coords);

        final Map<GreaterCoarseKey, T> inner;
        if (coords.equals(this.key0))
            inner = this.inner0;
        else {
            this.key0 = coords;
            inner = this.inner0 = this.space.get(lesserKey);
        }

        if (inner != null) try {
            final GreaterCoarseKey greaterKey = greaterKey(coords);
            final T value0 = inner.remove(greaterKey);
            if (value0 != null)
                this.size--;
            return value0;
        } finally {
            if (inner.isEmpty()) {
                this.space.remove(lesserKey);
                this.key0 = null;
                this.inner0 = null;
            }
        }
        return null;
    }
    @Override
    public T remove(Object key) {
        return remove((Vec3i)key);
    }

    @Override
    public void putAll(Map<? extends Vec3i, ? extends T> m) {
        for (Map.Entry<? extends Vec3i, ? extends T> entry: m.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
    public void clear() {
        this.space.clear();
        this.size = 0;
        this.inner0 = null;
        this.key0 = null;
    }

    public void cullOutside(IntAxisAlignedBox bounds) {
        final LesserCoarseKey
                min = lesserKey(bounds.min),
                max = lesserKey(bounds.max);

        final int size0 = this.size;

        final Iterator<Entry<LesserCoarseKey, Map<GreaterCoarseKey, T>>> i = this.space.entrySet().iterator();
        while (i.hasNext()) {
            final Entry<LesserCoarseKey, Map<GreaterCoarseKey, T>> entry = i.next();
            final LesserCoarseKey key = entry.getKey();
            final Map<GreaterCoarseKey, T> subMap = entry.getValue();
            if (key.x < min.x || key.y < min.y || key.z < min.z || key.x > max.x || key.y > max.y || key.z > max.z) {
                i.remove();
                size -= subMap.size();
            }
        }

        if (size0 != size) {
            this.inner0 = null;
            this.key0 = null;
        }
    }

    private abstract class AbstractIterator<V> extends FilterIterable.Iter<V> implements Iterator<V> {
        private Iterator<Map.Entry<LesserCoarseKey, Map<GreaterCoarseKey, T>>> oi;
        private Iterator<Map.Entry<GreaterCoarseKey, T>> ii;

        private LesserCoarseKey lesserKey;

        public AbstractIterator () {
            this.oi = SparseSpatialMap.this.space.entrySet().iterator();
        }

        @Override
        protected V findNext() {
            final Iterator<Entry<LesserCoarseKey, Map<GreaterCoarseKey, T>>> oi = this.oi;
            Iterator<Entry<GreaterCoarseKey, T>> ii = this.ii;

            while (ii == null || !ii.hasNext()) {
                if (oi.hasNext()) {
                    final Entry<LesserCoarseKey, Map<GreaterCoarseKey, T>> entry = oi.next();
                    this.lesserKey = entry.getKey();
                    ii = this.ii = entry.getValue().entrySet().iterator();
                } else
                    return null;
            }

            final Entry<GreaterCoarseKey, T> entry = ii.next();

            final Vec3i coords = coords(lesserKey, entry.getKey());
            return map(coords, entry.getValue());
        }

        protected abstract V map(Vec3i key, T value);
    }

    private final class KeySet extends AbstractSet<Vec3i> {
        private final class Iter extends AbstractIterator<Vec3i> {
            @Override
            protected final Vec3i map(Vec3i key, T value) {
                return key;
            }
        }

        @Override
        public Iterator<Vec3i> iterator() {
            return new Iter();
        }

        @Override
        public int size() {
            return SparseSpatialMap.this.size;
        }
    }

    @Override
    public Set<Vec3i> keySet() {
        return new KeySet();
    }

    private final class ValueCollection extends AbstractCollection<T> {
        private final class Iter extends AbstractIterator<T> {
            @Override
            protected final T map(Vec3i key, T value) {
                return value;
            }
        }

        @Override
        public Iterator<T> iterator() {
            return new Iter();
        }

        @Override
        public int size() {
            return SparseSpatialMap.this.size;
        }
    }

    @Override
    public Collection<T> values() {
        return new ValueCollection();
    }

    private final class EntrySet extends AbstractSet<Map.Entry<Vec3i, T>> {
        private final class Iter extends AbstractIterator<Map.Entry<Vec3i, T>> {
            @Override
            protected final Map.Entry<Vec3i, T> map(Vec3i key, T value) {
                return new AbstractMap.SimpleEntry<Vec3i, T>(key, value);
            }
        }

        @Override
        public Iterator<Map.Entry<Vec3i, T>> iterator() {
            return new Iter();
        }

        @Override
        public int size() {
            return SparseSpatialMap.this.size;
        }
    }

    @Override
    public Set<Map.Entry<Vec3i, T>> entrySet() {
        return new EntrySet();
    }

    private LesserCoarseKey lesserKey(Vec3i coords) {
        return new LesserCoarseKey(coords.x >> this.order, coords.y >> this.order, coords.z >> this.order);
    }

    private GreaterCoarseKey greaterKey(Vec3i coords) {
        return new GreaterCoarseKey(coords.x & this.mask, coords.y & this.mask, coords.z & this.mask);
    }

    private Vec3i coords(LesserCoarseKey lesserKey, GreaterCoarseKey greaterKey) {
        return new Vec3i(
                ((lesserKey.x << this.order) & ~this.mask) | greaterKey.x,
                ((lesserKey.y << this.order) & ~this.mask) | greaterKey.y,
                ((lesserKey.z << this.order) & ~this.mask) | greaterKey.z
        );
    }

    private Map<GreaterCoarseKey, T> acquireInner(Vec3i coords) {
        if (coords.equals(this.key0))
            return this.inner0;

        final LesserCoarseKey lesserKey = lesserKey(this.key0 = coords);
        return this.inner0 = this.space.get(lesserKey);
    }
}
