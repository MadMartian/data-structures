/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.misc;

/**
 * main
 *
 * Created by jonathan on 21/09/16.
 */
public final class Either<A, B> {
    public enum Projection {
        LEFT, RIGHT
    }

    public final A left;
    public final B right;

    private Either(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public static <A, B> Either<A, B> left(A left) { return left == null ? null : new Either<A, B> (left, null); }
    public static <A, B> Either<A, B> right(B right) { return right == null ? null : new Either<A, B> (null, right); }

    public static <B> B rightOf(Either<?, B> either) { return either == null ? null : either.right; }
    public static <A> A leftOf(Either<A, ?> either) { return either == null ? null : either.left; }

    public static boolean isRight(Either<?, ?> either) { return either != null && either.projection() == Projection.RIGHT; }
    public static boolean isLeft(Either<?, ?> either) { return either != null && either.projection() == Projection.LEFT; }

    public static <A, B, B2> Either<A, B2> mapRight(Either<A, B> either, B2 right) {
        return either == null ? null : either.right != null ? Either.<A, B2>right(right) : Either.<A, B2>left(either.left);
    }
    public static <A, B, A2> Either<A2, B> mapLeft(Either<A, B> either, A2 left) {
        return either == null ? null : either.left != null ? Either.<A2, B>left(left) : Either.<A2, B>right(either.right);
    }

    public static <A, B> Either<A, B> of (A left, B right) {
        return new Either<A, B> (left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Either)) return false;

        Either<?, ?> either = (Either<?, ?>) o;

        if (left != null ? !left.equals(either.left) : either.left != null) return false;
        return right != null ? right.equals(either.right) : either.right == null;

    }

    public final Projection projection() {
        if (this.left == null && this.right != null)
            return Projection.RIGHT;

        if (this.left != null && this.right == null)
            return Projection.LEFT;

        throw new IllegalStateException("Either is neither");
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (this.left != null)
            return this.left + " (left)";
        if (this.right != null)
            return this.right + " (right)";

        return null;
    }
}
