/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import com.extollit.linalg.ISpatialRegion;

import java.text.MessageFormat;

import static java.lang.Math.abs;

public final class Vec3i implements ISpatialRegion {
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);

    public final int x, y, z;

    public Vec3i(Vec3i copy)
    {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    public Vec3i(com.extollit.linalg.mutable.Vec3i copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    public Vec3i(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i(VertexOffset offset) {
        this.x = offset.dx;
        this.y = offset.dy;
        this.z = offset.dz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3i vec3i = (Vec3i) o;

        if (x != vec3i.x) return false;
        if (y != vec3i.y) return false;
        return z == vec3i.z;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("<{0,number,#}, {1,number,#}, {2,number,#}>", x, y, z);
    }

    public Vec3i plusOf(VertexOffset other)
    {
        return
            new Vec3i(
                this.x + other.dx,
                this.y + other.dy,
                this.z + other.dz
            );
    }

    public Vec3i subOf(VertexOffset other)
    {
        return
            new Vec3i(
                this.x - other.dx,
                this.y - other.dy,
                this.z - other.dz
            );
    }

    public Vec3i plusOf(Vec3i other)
    {
        return
            new Vec3i(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z
            );
    }

    public Vec3i plusOf(int delta)
    {
        return
                new Vec3i(
                        this.x + delta,
                        this.y + delta,
                        this.z + delta
                );
    }

    public Vec3i subOf(Vec3i other)
    {
        return
            new Vec3i(
                this.x - other.x,
                this.y - other.y,
                this.z - other.z
            );
    }

    public Vec3i subOf(int delta)
    {
        return
                new Vec3i(
                        this.x - delta,
                        this.y - delta,
                        this.z - delta
                );
    }

    public int dot(Vec3i other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
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

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public int taxiTo(Vec3i other) {
        return abs(other.x - x) + abs(other.y - y) + abs(other.z - z);
    }

    public static Vec3i truncated(Vec3d other) {
        return new Vec3i((int)other.x, (int)other.y, (int)other.z);
    }

    public boolean truncatedEquals(double x, double y, double z) {
        return this.x == (int)x && this.y == (int)y && this.z == (int)z;
    }

    public static Vec3i min(Vec3i min, Vec3i max) {
        return new Vec3i(Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z));
    }
    public static Vec3i max(Vec3i min, Vec3i max) {
        return new Vec3i(Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
    }
    public static Vec3i min(int x0, int y0, int z0, int xN, int yN, int zN) {
        return new Vec3i(Math.min(x0, xN), Math.min(y0, yN), Math.min(z0, zN));
    }
    public static Vec3i max(int x0, int y0, int z0, int xN, int yN, int zN) {
        return new Vec3i(Math.max(x0, xN), Math.max(y0, yN), Math.max(z0, zN));
    }
}
