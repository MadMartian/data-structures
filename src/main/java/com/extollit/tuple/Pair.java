package com.extollit.tuple;

/**
 * MadMartian Mod
 *
 * Created by jonny on 16/04/2015.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class Pair< L, R >
{
    public L left;
    public R right;

    public static class Sealed< L, R > {
        public final L left;
        public final R right;

        protected Sealed(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public static < L, R > Sealed<L, R> of (L left, R right) {
            return new Sealed<L, R>(left, right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair.Sealed<?, ?>)) return false;

            Pair.Sealed<?, ?> pair = (Pair.Sealed<?, ?>) o;

            if (left != null ? !left.equals(pair.left) : pair.left != null) return false;
            return !(right != null ? !right.equals(pair.right) : pair.right != null);

        }

        @Override
        public int hashCode() {
            int result = left != null ? left.hashCode() : 0;
            result = 31 * result + (right != null ? right.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return '(' + (this.left == null ? null : this.left.toString()) + ',' + (this.right == null ? null : this.right.toString()) + ')';
        }
    }

    public Pair(L left, R right)
    {
        this.left = left;
        this.right = right;
    }

    public static < L, R > Pair< L, R > of(L left, R right)
    {
        return new Pair<L, R>(left, right);
    }

    public Sealed<L, R> sealed() { return new Sealed<L, R>(this.left, this.right); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (left != null ? !left.equals(pair.left) : pair.left != null) return false;
        return !(right != null ? !right.equals(pair.right) : pair.right != null);

    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return '(' + (this.left == null ? null : this.left.toString()) + ',' + (this.right == null ? null : this.right.toString()) + ')';
    }

    public <L2> Pair<L2, R> map_left(L2 left)
    {
        return Pair.of(left, right);
    }
    public <R2> Pair<L, R2> map_right(R2 right)
    {
        return Pair.of(left, right);
    }
}
