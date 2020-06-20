/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import com.extollit.linalg.mutable.Vec3i;

import java.text.MessageFormat;

public final class VertexOffset {
    public final byte dx, dy, dz;

    public VertexOffset() {
        this(1, 0, 0);
    }
    public VertexOffset(int dx, int dy, int dz) {
        assert dx >= -1 && dx <= 1;
        assert dy >= -1 && dy <= 1;
        assert dz >= -1 && dz <= 1;

        this.dx = (byte) dx;
        this.dy = (byte) dy;
        this.dz = (byte) dz;
    }

    public VertexOffset mask() {
        return new VertexOffset(
            ~~((this.dx & 1) - 1),
            ~~((this.dy & 1) - 1),
            ~~((this.dz & 1) - 1)
        );
    }

    public VertexOffset pcross(VertexOffset other) {
        final int
            dx = this.dy *other.dz - this.dz *other.dy,
            dy = this.dz *other.dx - this.dx *other.dz,
            dz = this.dx *other.dy - this.dy *other.dx;

        return new VertexOffset(dx, dy, dz);
    }

    public VertexOffset orthog() {
        return new VertexOffset(
            dy ^ dz,
            dx ^ dz,
            dy ^ dx
        );
    }

    public Vec3i offset(Vec3i vec) {
        vec.x += dx;
        vec.y += dy;
        vec.z += dz;
        return vec;
    }

    public VertexOffset sq() {
        return new VertexOffset(this.dx*this.dx, this.dy*this.dy, this.dz*this.dz);
    }

    public VertexOffset mul(VertexOffset offset) {
        return new VertexOffset(
            this.dx*offset.dx,
            this.dy*offset.dy,
            this.dz*offset.dz
        );
    }

    public VertexOffset sub(VertexOffset offset) {
        return
            new VertexOffset(
                this.dx - offset.dx,
                this.dy - offset.dy,
                this.dz - offset.dz
            );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexOffset that = (VertexOffset) o;

        return ((this.dx ^ that.dx) | (this.dy ^ that.dy) | (this.dz ^ that.dz)) == 0;
    }

    @Override
    public int hashCode() {
        int result = (int) dx;
        result = 31 * result + (int) dy;
        result = 31 * result + (int) dz;
        return result;
    }

    @Override
    public String toString() {
        return MessageFormat.format("< {0}, {1}, {2} >", dx, dy, dz);
    }
}
