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
        this.min = Vec3i.min(x0, y0, z0, xN, yN, zN);
        this.max = Vec3i.max(x0, y0, z0, xN, yN, zN);
    }
    public IntAxisAlignedBox(Vec3i min, Vec3i max) {
        this.min = Vec3i.min(min, max);
        this.max = Vec3i.max(min, max);
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
        return intersects(other.min, other.max);
    }

    public boolean intersects(final Vec3i min, final Vec3i max) {
        return intersects(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public boolean intersects(final int x0, final int y0, final int z0, final int xN, final int yN, final int zN) {
        return
            (
                lineDeltaFactor(this.min.x, this.max.x, x0, xN) |
                lineDeltaFactor(this.min.y, this.max.y, y0, yN) |
                lineDeltaFactor(this.min.z, this.max.z, z0, zN)
            ) == 0;
    }

    private static int lineDeltaFactor(int leftMin, int leftMax, int rightMin, int rightMax) {
        final int
                leftWidth = leftMax - leftMin + 1,
                rightWidth = rightMax - rightMin + 1,

                leftMid = leftMin + ((leftMax - leftMin) >> 1),
                rightMid = rightMin + ((rightMax - rightMin) >> 1);

        return (FastMath.abs(leftMid - rightMid) << 1) / (leftWidth + rightWidth);
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
