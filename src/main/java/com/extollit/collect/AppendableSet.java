package com.extollit.collect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AppendableSet<T> implements Iterable<T> {
    private final Set<T> existing = new HashSet<T>();
    private Set<T> neww = new HashSet<T>();

    public AppendableSet() {}

    public Set<T> flush() {
        Set<T> flush = this.neww;
        this.neww = new HashSet<T>();
        this.existing.addAll(flush);
        return flush;
    }

    public final Set<T> dirtySubSet() { return Collections.unmodifiableSet(this.neww); }
    public final Set<T> unchangedSubSet() { return Collections.unmodifiableSet(this.existing); }

    private class Iter implements Iterator<T> {
        private final Iterator<T> existing, neww;

        private Iter() {
            this.existing = AppendableSet.this.existing.iterator();
            this.neww = AppendableSet.this.neww.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.existing.hasNext() || this.neww.hasNext();
        }

        @Override
        public T next() {
            if (this.existing.hasNext())
                return this.existing.next();
            else
                return this.neww.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    public boolean insert(T element) {
        return !this.existing.contains(element) && this.neww.add(element);
    }

    public boolean restore(T element) {
        return !this.neww.contains(element) && this.existing.add(element);

    }

    public boolean contains(T element) {
        if (this.neww.size() > this.existing.size())
            return this.existing.contains(element) || this.neww.contains(element);
        else
            return this.neww.contains(element) || this.existing.contains(element);
    }

    public final boolean dirty() { return !this.neww.isEmpty(); }

    public final int totalSize() {
        return this.existing.size() + this.neww.size();
    }

    public final int dirtySize() {
        return this.neww.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppendableSet<?> that = (AppendableSet<?>) o;

        if (!existing.equals(that.existing)) return false;
        return neww.equals(that.neww);
    }

    @Override
    public int hashCode() {
        int result = existing.hashCode();
        result = 31 * result + neww.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.existing.toString() + " ++ " + this.neww.toString();
    }
}
