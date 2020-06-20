/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import com.extollit.linalg.ISpatialRegion;

import java.text.MessageFormat;

public final class Vec2d implements ISpatialRegion {
    public final double x, y;

    public Vec2d(Vec2d copy)
    {
        this.x = copy.x;
        this.y = copy.y;
    }
    public Vec2d(com.extollit.linalg.mutable.Vec3d copy) {
        this.x = copy.x;
        this.y = copy.y;
    }
    public Vec2d(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vec2d(Vec3i copy) {
        this.x = copy.x;
        this.y = copy.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec2d vec3d = (Vec2d) o;

        if (Double.compare(vec3d.x, x) != 0) return false;
        return Double.compare(vec3d.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("<{0},{1}>", x, y);
    }

    public Vec2d subOf(Vec2d other)
    {
        return
            new Vec2d(
                this.x - other.x,
                this.y - other.y
            );
    }

    public Vec2d plusOf(Vec2d other)
    {
        return
            new Vec2d(
                this.x + other.x,
                this.y + other.y
            );
    }

    public double dot(Vec2d other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vec2d mulOf(double x, double y, double z) {
        return new Vec2d(
            this.x * x,
            this.y * y
        );
    }

    public double mg2d () { return this.x*this.x + this.y*this.y; }

    public Vec2d squared() {
        return
            new Vec2d(
                this.x * this.x,
                this.y * this.y
            );
    }

    @Override
    public boolean contains(double x, double y, double z) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean contains(Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(com.extollit.linalg.mutable.Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean contains(Vec3i coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(com.extollit.linalg.mutable.Vec3i coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public final void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
