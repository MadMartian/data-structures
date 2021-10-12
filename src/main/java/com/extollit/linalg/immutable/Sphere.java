/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import com.extollit.linalg.AbstractSpatialRegion;
import com.extollit.linalg.ISpatialRegion;

import java.text.MessageFormat;

/**
 * main
 *
 * Created by jonathan on 12/09/16.
 */
public final class Sphere extends AbstractSpatialRegion implements ISpatialRegion {
    public final Vec3d point;
    public final double radius;

    private final double rsq;

    public Sphere(Vec3d point, double radius) {
        this.point = point;
        this.radius = radius;
        this.rsq = radius*radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere.radius, radius) != 0) return false;
        return point.equals(sphere.point);

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(radius);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public final boolean contains(double x, double y, double z) {
        x -= this.point.x;
        y -= this.point.y;
        z -= this.point.z;

        return x*x + y*y + z*z < this.rsq;
    }

    public Vec3d center() {
        return this.point;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} about {1}", this.radius, this.point);
    }
}
