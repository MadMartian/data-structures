package com.extollit.linalg.mutable;

import com.extollit.linalg.AbstractSpatialRegion;
import com.extollit.linalg.ISpatialRegion;

/**
 * Created by jonny on 10/09/2016.
 */
public final class AxisAlignedBBox extends AbstractSpatialRegion implements ISpatialRegion {
    public final Vec3d min, max;

    public AxisAlignedBBox(double x0, double y0, double z0, double xN, double yN, double zN) {
        this(
            new Vec3d(
                    Math.min(x0, xN),
                    Math.min(y0, yN),
                    Math.min(z0, zN)
            ),
            new Vec3d(
                    Math.max(xN, x0),
                    Math.max(yN, y0),
                    Math.max(zN, z0)
            )
        );
    }
    public AxisAlignedBBox(Vec3d min, Vec3d max) {
        if (min.x > max.x || min.y > max.y || min.z > max.z) {
            this.min = new Vec3d(
                    Math.min(min.x, max.x),
                    Math.min(min.y, max.y),
                    Math.min(min.z, max.z)
            );
            this.max = new Vec3d(
                    Math.max(max.x, min.x),
                    Math.max(max.y, min.y),
                    Math.max(max.z, min.z)
            );
        } else {
            this.min = min;
            this.max = max;
        }
    }

    public AxisAlignedBBox(com.extollit.linalg.immutable.AxisAlignedBBox immutable) {
        this.min = new Vec3d(immutable.min);
        this.max = new Vec3d(immutable.max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AxisAlignedBBox that = (AxisAlignedBBox) o;

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
    public final boolean contains(double x, double y, double z) {
        return
            x >= this.min.x && x <= this.max.x &&
            y >= this.min.y && y <= this.max.y &&
            z >= this.min.z && z <= this.max.z;
    }

    public double mg2(com.extollit.linalg.mutable.AxisAlignedBBox other) {
        final com.extollit.linalg.mutable.Vec3d
                left0 = this.min,
                right0 = other.min,
                leftN = this.max,
                rightN = other.max;

        double
                dx = Math.max(left0.x - rightN.x, right0.x - leftN.x),
                dy = Math.max(left0.y - rightN.y, right0.y - leftN.y),
                dz = Math.max(left0.z - rightN.z, right0.z - leftN.z);

        if (dx < 0)
            dx = 0;
        if (dy < 0)
            dy = 0;
        if (dz < 0)
            dz = 0;

        return dx * dx + dy * dy + dz * dz;
    }

    public com.extollit.linalg.immutable.Vec3d center() {
        final Vec3d
                min = this.min,
                max = this.max;

        return new com.extollit.linalg.immutable.Vec3d(
                (max.x + min.x) / 2,
                (max.y + min.y) / 2,
                (max.z + min.z) / 2
        );
    }

    private Vec3d intersection(final double d1, final double d2, final com.extollit.linalg.immutable.Vec3d p1, com.extollit.linalg.immutable.Vec3d p2) {
        if (d1 * d2 >= 0 || d1 == d2)
            return null;

        final double f = -d1 / (d2 - d1);
        return new Vec3d(
            p1.x + (p2.x - p1.x) * f,
            p1.y + (p2.y - p1.y) * f,
            p1.z + (p2.z - p1.z) * f
        );
    }

    public Vec3d intersection(com.extollit.linalg.immutable.Vec3d p0, com.extollit.linalg.immutable.Vec3d pN) {
        final Vec3d
            min = this.min,
            max = this.max;

        Vec3d i;

        i = intersection(p0.x - min.x, pN.x - min.x, p0, pN);
        if (i != null && i.z > min.z && i.z < max.z && i.y > min.y && i.y < max.y)
            return i;

        i = intersection(p0.y - min.y, pN.y - min.y, p0, pN);
        if (i != null && i.z > min.z && i.z < max.z && i.x > min.x && i.x < max.x)
            return i;

        i = intersection(p0.z - min.z, pN.z - min.z, p0, pN);
        if (i != null && i.x > min.x && i.x < max.x && i.y > min.y && i.y < max.y)
            return i;

        i = intersection(p0.x - max.x, pN.x - max.x, p0, pN);
        if (i != null && i.z > min.z && i.z < max.z && i.y > min.y && i.y < max.y)
            return i;

        i = intersection(p0.y - max.y, pN.y - max.y, p0, pN);
        if (i != null && i.z > min.z && i.z < max.z && i.x > min.x && i.x < max.x)
            return i;

        i = intersection(p0.z - max.z, pN.z - max.z, p0, pN);
        if (i != null && i.x > min.x && i.x < max.x && i.y > min.y && i.y < max.y)
            return i;

        return null;
    }

    @Override
    public final void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void expandBy(double f) {
        this.min.sub(f, f, f);
        this.max.add(f, f, f);
    }

    public void sub(com.extollit.linalg.immutable.Vec3d v) {
        this.min.sub(v);
        this.max.sub(v);
    }

    public void add(com.extollit.linalg.immutable.Vec3d v) {
        this.min.add(v);
        this.max.add(v);
    }

    public void add(double dx, double dy, double dz) {
        this.min.add(dx, dy, dz);
        this.max.add(dx, dy, dz);
    }
}
