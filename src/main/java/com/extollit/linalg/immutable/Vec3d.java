/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import com.extollit.linalg.ISpatialRegion;

import java.text.MessageFormat;

public final class Vec3d implements ISpatialRegion {
    public final double x, y, z;

    public Vec3d(Vec3d copy)
    {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    public Vec3d(com.extollit.linalg.mutable.Vec3d copy) {
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

    public Vec3d(Vec3i copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
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
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("<{0,number,#.#}, {1,number,#.#}, {2,number,#.#}>", x, y, z);
    }

    public final Vec3d proj(com.extollit.linalg.mutable.Vec3d other) {
        return mulOf(dot(other) / mg2());
    }

    public final Vec3d proj(Vec3d other) {
        return mulOf(dot(other) / mg2());
    }

    public final double mg2 () { return this.x*this.x + this.y*this.y + this.z*this.z; }

    public Vec3d subOf(Vec3d other)
    {
        return
            new Vec3d(
                this.x - other.x,
                this.y - other.y,
                this.z - other.z
            );
    }

    public Vec3d subOf(com.extollit.linalg.mutable.Vec3d other)
    {
        return subOf(other.x, other.y, other.z);
    }

    public Vec3d subOf(double x, double y, double z) {
        return
                new Vec3d(
                        this.x - x,
                        this.y - y,
                        this.z - z
                );
    }

    public Vec3d plusOf(Vec3d other)
    {
        return
            new Vec3d(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z
            );
    }

    public Vec3d plusOf(com.extollit.linalg.mutable.Vec3d other)
    {
        return plusOf(other.x, other.y, other.z);
    }

    public Vec3d plusOf(double dx, double dy, double dz) {
        return new Vec3d(this.x + dx, this.y + dy, this.z + dz);
    }

    public double dot(Vec3d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public double dot(com.extollit.linalg.mutable.Vec3d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec3d mulOf(double s) {
        return mulOf(s, s, s);
    }

    public Vec3d mulOf(double x, double y, double z) {
        return new Vec3d(
            this.x * x,
            this.y * y,
            this.z * z
        );
    }

    public Vec3d mulOf(Vec3i other) {
        return 
            new Vec3d(
                this.x * (double)other.x,
                this.y * (double)other.y,
                this.z * (double)other.z
            );
    }

    public Vec3d mulOf(com.extollit.linalg.mutable.Vec3i other) {
        return
                new Vec3d(
                        this.x * (double)other.x,
                        this.y * (double)other.y,
                        this.z * (double)other.z
                );
    }

    public final com.extollit.linalg.mutable.Vec3d mutableCrossOf(Vec3d other) {
        return new com.extollit.linalg.mutable.Vec3d(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    public final void cross(com.extollit.linalg.mutable.Vec3d other) {
        final double
            x = this.y * other.z - this.z * other.y,
            y = this.z * other.x - this.x * other.z,
            z = this.x * other.y - this.y * other.x;

        other.x = x;
        other.y = y;
        other.z = z;
    }

    public double mg () { return Math.sqrt(mg2()); }

    public Vec3d squared() {
        return
            new Vec3d(
                this.x * this.x,
                this.y * this.y,
                this.z * this.z
            );
    }

    public Vec3d negated() {
        return new Vec3d(-this.x, -this.y, -this.z);
    }

    public Vec3d normalized() {
        final double mg = mg();

        return new Vec3d(this.x / mg, this.y / mg, this.z / mg);
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
