/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.mutable;

import com.extollit.linalg.ISpatialRegion;
import com.extollit.linalg.immutable.VertexOffset;

import java.text.MessageFormat;

public final class Vec3d implements ISpatialRegion {
    public double x, y, z;

    public Vec3d(Vec3d copy)
    {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    public Vec3d(com.extollit.linalg.immutable.Vec3d copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    public Vec3d(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d(VertexOffset offset) {
        this.x = offset.dx;
        this.y = offset.dy;
        this.z = offset.dz;
    }

    public Vec3d(Vec3i coords) {
        this(coords.x, coords.y, coords.z);
    }

    public Vec3d(com.extollit.linalg.immutable.Vec3i coords) {
        this(coords.x, coords.y, coords.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3d vec3d = (Vec3d) o;

        if (Double.compare(vec3d.x, x) != 0) return false;
        if (Double.compare(vec3d.y, y) != 0) return false;
        return Double.compare(vec3d.z, z) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("<{0,number,#.#}, {1,number,#.#}, {2,number,#.#}>", x, y, z);
    }

    public void set(com.extollit.linalg.immutable.Vec3d other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public void set(com.extollit.linalg.mutable.Vec3d other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public void set(com.extollit.linalg.immutable.Vec3i other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final void sub(Vec3d other)
    {
        sub(other.x, other.y, other.z);
    }

    public void sub(com.extollit.linalg.immutable.Vec3d other) {
        sub(other.x, other.y, other.z);
    }

    public void sub(com.extollit.linalg.immutable.Vec3i other) {
        sub(other.x, other.y, other.z);
    }

    public final void sub(double x, double y, double z)
    {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public final void add(Vec3d other)
    {
        add(other.x, other.y, other.z);
    }

    public final void add(com.extollit.linalg.immutable.Vec3d other)
    {
        add(other.x, other.y, other.z);
    }

    public final void add(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public final double dot(Vec3d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public final double dot(com.extollit.linalg.immutable.Vec3d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public final void mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    public final void mul(Vec3i other) {
        this.x *= (double)other.x;
        this.y *= (double)other.y;
        this.z *= (double)other.z;
    }

    public final void mul(double f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public final void cross(Vec3d other) {
        double
            x = this.y * other.z - this.z * other.y,
            y = this.z * other.x - this.x * other.z,
            z = this.x * other.y - this.y * other.x;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final void cross(com.extollit.linalg.immutable.Vec3d other) {
        double
                x = this.y * other.z - this.z * other.y,
                y = this.z * other.x - this.x * other.z,
                z = this.x * other.y - this.y * other.x;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final double mg2 () { return this.x*this.x + this.y*this.y + this.z*this.z; }
    public final double magnitude() { return Math.sqrt(mg2()); }

    public final void proj(Vec3d other) {
        mul(dot(other) / mg2());
    }

    public final void proj(com.extollit.linalg.immutable.Vec3d other) {
        mul(dot(other) / mg2());
    }

    public final Vec3d projcp(Vec3d other) {
        double f = dot(other) / mg2();
        return new Vec3d(
            this.x * f,
            this.y * f,
            this.z * f
        );
    }

    public final void sq() {
        this.x *= this.x;
        this.y *= this.y;
        this.z *= this.z;
    }

    public final void normalize() {
        double length = magnitude();
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    @Override
    public boolean contains(double x, double y, double z) {
        return this.x == x && this.y == y && this.z == z;
    }

    @Override
    public boolean contains(com.extollit.linalg.immutable.Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(com.extollit.linalg.mutable.Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }

    @Override
    public boolean contains(com.extollit.linalg.immutable.Vec3i coordinates) {
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

    public double mg() {
        return Math.sqrt(mg2());
    }
}
