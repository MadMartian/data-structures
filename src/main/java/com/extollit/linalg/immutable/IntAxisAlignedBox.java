/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import com.extollit.linalg.AbstractSpatialRegion;
import com.extollit.linalg.ISpatialRegion;
import com.extollit.num.FastMath;

/**
 * main
 * <p/>
 * Created by jonathan on 28/11/16.
 */
public class IntAxisAlignedBox extends AbstractSpatialRegion implements ISpatialRegion {
    public final Vec3i min, max;

    public IntAxisAlignedBox(int x0, int y0, int  z0, int xN, int yN, int zN) {
        this(new Vec3i(x0, y0, z0), new Vec3i(xN, yN, zN));
    }
    public IntAxisAlignedBox(Vec3i min, Vec3i max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntAxisAlignedBox that = (IntAxisAlignedBox) o;

        if (!min.equals(that.min)) return false;
        return max.equals(that.max);

    }

    @Override
    public int hashCode() {
        int result = min.hashCode();
        result = 31 * result + max.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.min + " to " + this.max;
    }

    @Override
    public final boolean contains(int x, int y, int z) {
        return
                x >= this.min.x && x <= this.max.x &&
                y >= this.min.y && y <= this.max.y &&
                z >= this.min.z && z <= this.max.z;
    }

    public Vec3d center() {
        final Vec3i
                min = this.min,
                max = this.max;

        return new Vec3d(
                (float)(max.x + min.x) / 2,
                (float)(max.y + min.y) / 2,
                (float)(max.z + min.z) / 2
        );
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean valid() {
        return min.x <= max.x && min.y <= max.y && min.z <= max.z;
    }

    @Override
    public final boolean contains(double x, double y, double z) {
        return
                x >= this.min.x && x <= this.max.x &&
                y >= this.min.y && y <= this.max.y &&
                z >= this.min.z && z <= this.max.z;
    }

    public final boolean contains(IntAxisAlignedBox other) {
        return contains(other.min) && contains(other.max);
    }

    public boolean intersects(IntAxisAlignedBox other) {
        return
            (
                lineDeltaFactor(this.min.x, this.max.x, other.min.x, other.max.x) |
                lineDeltaFactor(this.min.y, this.max.y, other.min.y, other.max.y) |
                lineDeltaFactor(this.min.z, this.max.z, other.min.z, other.max.z)
            ) == 0;
    }

    private static int lineDeltaFactor(int leftMin, int leftMax, int rightMin, int rightMax) {
        assert leftMin >= Integer.MIN_VALUE >> 1 && leftMax <= Integer.MAX_VALUE >> 1;
        assert rightMin >= Integer.MIN_VALUE >> 1 && rightMax <= Integer.MAX_VALUE >> 1;

        final int
                leftWidth = leftMax - leftMin,
                rightWidth = rightMax - rightMin,

                leftMid = leftMin + ((leftMax - leftMin) >> 1),
                rightMid = rightMin + ((rightMax - rightMin) >> 1);

        return (FastMath.abs(leftMid - rightMid) << 1) / (leftWidth + rightWidth + 1);
    }

    public IntAxisAlignedBox intersection(IntAxisAlignedBox other) {
        return new IntAxisAlignedBox(
            Math.max(min.x, other.min.x),
            Math.max(min.y, other.min.y),
            Math.max(min.z, other.min.z),

            Math.min(max.x, other.max.x),
            Math.min(max.y, other.max.y),
            Math.min(max.z, other.max.z)
        );
    }

    public Vec3i midpoint() {
        return new Vec3i(
            (max.x + min.x) / 2,
            (max.y + min.y) / 2,
            (max.z + min.z) / 2
        );
    }

    public IntAxisAlignedBox union(IntAxisAlignedBox other) {
        return new IntAxisAlignedBox(
            Math.min(this.min.x, other.min.x),
            Math.min(this.min.y, other.min.y),
            Math.min(this.min.z, other.min.z),

            Math.max(this.max.x, other.max.x),
            Math.max(this.max.y, other.max.y),
            Math.max(this.max.z, other.max.z)
        );
    }

    public IntAxisAlignedBox union(int x, int y, int z) {
        return new IntAxisAlignedBox(
            Math.min(this.min.x, x),
            Math.min(this.min.y, y),
            Math.min(this.min.z, z),

            Math.max(this.max.x, x),
            Math.max(this.max.y, y),
            Math.max(this.max.z, z)
        );
    }
}
