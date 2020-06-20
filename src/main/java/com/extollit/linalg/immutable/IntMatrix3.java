/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

public final class IntMatrix3 {
    public static final IntMatrix3 IDENTITY = new IntMatrix3(
            1, 0 ,0,
            0, 1, 0,
            0, 0, 1
    );

    public final int  m00, m01, m02,
                      m10, m11, m12,
                      m20, m21, m22;

    public IntMatrix3(
        int m00, int m01, int m02,
        int m10, int m11, int m12,
        int m20, int m21, int m22
    )
    {
        this.m00 = m00;         this.m01 = m01;         this.m02 = m02;
        this.m10 = m10;         this.m11 = m11;         this.m12 = m12;
        this.m20 = m20;         this.m21 = m21;         this.m22 = m22;
    }

    public IntMatrix3(IntMatrix3 copy)
    {
        this(copy.m00, copy.m01, copy.m02,
             copy.m10, copy.m11, copy.m12,
             copy.m20, copy.m21, copy.m22);
    }
    public IntMatrix3(com.extollit.linalg.mutable.IntMatrix3 copy)
    {
        this(copy.m00, copy.m01, copy.m02,
             copy.m10, copy.m11, copy.m12,
             copy.m20, copy.m21, copy.m22);
    }

    public final IntMatrix3 mulOf(IntMatrix3 o) {
        int
            d00 = m00 * o.m00 + m01 * o.m10 + m02 * o.m20,
            d01 = m00 * o.m01 + m01 * o.m11 + m02 * o.m21,
            d02 = m00 * o.m02 + m01 * o.m12 + m02 * o.m22,

            d10 = m10 * o.m00 + m11 * o.m10 + m12 * o.m20,
            d11 = m10 * o.m01 + m11 * o.m11 + m12 * o.m21,
            d12 = m10 * o.m02 + m11 * o.m12 + m12 * o.m22,

            d20 = m20 * o.m00 + m21 * o.m10 + m22 * o.m20,
            d21 = m20 * o.m01 + m21 * o.m11 + m22 * o.m21,
            d22 = m20 * o.m02 + m21 * o.m12 + m22 * o.m22;

        return new IntMatrix3(
            d00,     d01,     d02,
            d10,     d11,     d12,
            d20,     d21,     d22
        );
    }

    public final IntMatrix3 plusOf(IntMatrix3 o)
    {
        return new IntMatrix3(
            this.m00 + o.m00,      this.m01 + o.m01,      this.m02 + o.m02,
            this.m10 + o.m10,      this.m11 + o.m11,      this.m12 + o.m12,
            this.m20 + o.m20,      this.m21 + o.m21,      this.m22 + o.m22
        );        
    }
    
    public final IntMatrix3 timesOf(int value)
    {
        return new IntMatrix3(
            m00 * value,
            m01 * value,
            m02 * value,
    
            m10 * value,
            m11 * value,
            m12 * value,
    
            m20 * value,
            m21 * value,
            m22 * value
        );
    }
    public IntMatrix3 squared() {
        return mulOf(this);
    }

    public final IntMatrix3 inverted() {
        return new IntMatrix3(
            this.m00,       this.m10,      this.m20,
            this.m01,       this.m11,      this.m21,
            this.m02,       this.m12,      this.m22
        );
    }

    @Override
    public int hashCode() {
        long x = 1L;
        x = 31L * x + this.m00;
        x = 31L * x + this.m01;
        x = 31L * x + this.m02;
        x = 31L * x + this.m10;
        x = 31L * x + this.m11;
        x = 31L * x + this.m12;
        x = 31L * x + this.m20;
        x = 31L * x + this.m21;
        x = 31L * x + this.m22;
        return (int)(x ^ x >> 32);
    }

    protected final boolean equals(IntMatrix3 o)
    {
        try {
            return this.m00 == o.m00 && this.m01 == o.m01 && this.m02 == o.m02 &&
                   this.m10 == o.m10 && this.m11 == o.m11 && this.m12 == o.m12 &&
                   this.m20 == o.m20 && this.m21 == o.m21 && this.m22 == o.m22;
        } catch (NullPointerException var3) {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof IntMatrix3 && equals((IntMatrix3)obj);
    }

    @Override
    public String toString() {
        return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" +
               this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" +
               this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
    }

    public final Vec3d transformed(Vec3d v)
    {
        return new Vec3d(
            this.m00 * v.x + this.m01 * v.y + this.m02 * v.z,
            this.m10 * v.x + this.m11 * v.y + this.m12 * v.z,
            this.m20 * v.x + this.m21 * v.y + this.m22 * v.z
        );
    }

    public final Vec3i transformed(Vec3i v)
    {
        return new Vec3i(
            this.m00 * v.x + this.m01 * v.y + this.m02 * v.z,
            this.m10 * v.x + this.m11 * v.y + this.m12 * v.z,
            this.m20 * v.x + this.m21 * v.y + this.m22 * v.z
        );
    }

    public VertexOffset transform(VertexOffset v) {
        int x = this.m00 * v.dx + this.m01 * v.dy + this.m02 * v.dz;
        int y = this.m10 * v.dx + this.m11 * v.dy + this.m12 * v.dz;
        int z = this.m20 * v.dx + this.m21 * v.dy + this.m22 * v.dz;
        return new VertexOffset(x, y, z);
    }
}
