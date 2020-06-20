/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.mutable;

import com.extollit.linalg.ISpatialRegion;
import com.extollit.linalg.immutable.VertexOffset;

import java.text.MessageFormat;

public final class Vec3i implements ISpatialRegion {
    public int x, y, z;

    public Vec3i(Vec3i copy)
    {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    public Vec3i(com.extollit.linalg.immutable.Vec3i copy) {
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

    public void add(VertexOffset other)
    {
        this.x += other.dx;
        this.y += other.dy;
        this.z += other.dz;
    }

    public void sub(VertexOffset other)
    {
        this.x -= other.dx;
        this.y -= other.dy;
        this.z -= other.dz;
    }

    public void add(Vec3i other)
    {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    public void sub(Vec3i other)
    {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    public void add(com.extollit.linalg.immutable.Vec3i other)
    {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    public void sub(com.extollit.linalg.immutable.Vec3i other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
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

    public int taxiTo(Vec3i other) {
        return (other.x - x) + (other.y - y) + (other.z - z);
    }
}
