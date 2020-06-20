/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.mutable;

import com.extollit.linalg.ISpatialRegion;

import java.text.MessageFormat;

public final class Vec2d implements ISpatialRegion {
    public double x, y;

    public Vec2d(Vec2d copy)
    {
        this.x = copy.x;
        this.y = copy.y;
    }
    public Vec2d(com.extollit.linalg.immutable.Vec2d copy) {
        this.x = copy.x;
        this.y = copy.y;
    }
    public Vec2d(double x, double y)
    {
        this.x = x;
        this.y = y;
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

    public final void sub(Vec2d other)
    {
        sub(other.x, other.y);
    }

    public final void sub(double x, double y)
    {
        this.x -= x;
        this.y -= y;
    }

    public final void add(Vec2d other)
    {
        add(other.x, other.y);
    }

    public final void add(com.extollit.linalg.immutable.Vec2d other)
    {
        add(other.x, other.y);
    }

    public final void add(double x, double y)
    {
        this.x += x;
        this.y += y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final double dot(Vec2d other) {
        return this.x * other.x + this.y * other.y;
    }

    public final void mul(double x, double y) {
        this.x *= x;
        this.y *= y;
    }

    public final void mul(Vec3i other) {
        this.x *= (double)other.x;
        this.y *= (double)other.y;
    }

    public final void mul(double f) {
        this.x *= f;
        this.y *= f;
    }

    public final double mg2 () { return this.x*this.x + this.y*this.y; }
    public final double magnitude() { return Math.sqrt(mg2()); }

    public final void proj(Vec2d other) {
        mul(dot(other) / mg2());
    }

    public final Vec2d projcp(Vec2d other) {
        double f = dot(other) / mg2();
        return new Vec2d(
            this.x * f,
            this.y * f
        );
    }

    public final void sq() {
        this.x *= this.x;
        this.y *= this.y;
    }

    public final void normalize() {
        double length = magnitude();
        this.x /= length;
        this.y /= length;
    }

    @Override
    public boolean contains(double x, double y, double z) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean contains(com.extollit.linalg.immutable.Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean contains(com.extollit.linalg.immutable.Vec3i coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(Vec3i coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public final void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
