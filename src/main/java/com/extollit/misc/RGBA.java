package com.extollit.misc;

/**
 * MadMartian Mod
 *
 * Created by jonathann on 15-10-19.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class RGBA extends RGB {
    public short alpha;

    public RGBA(RGB rgb, float alpha)
    {
        super(rgb.toInt());
        this.alpha = toShort(alpha);
    }
    public RGBA(float red, float green, float blue, float alpha) {
        super(red, green, blue);
        this.alpha = toShort(alpha);
    }

    public float alpha() { return toFloat(alpha); }

    public RGBA(int integer) {
        super(integer & 0xFFFFFF);
        this.alpha = (short)(integer >> 24 & 0xFF);
    }

    public RGB scale(float f) {
        super.scale(f);
        this.alpha = scale(this.alpha, f);
        return this;
    }

    public int toInt() {
        return (super.toInt() & 0xFFFFFF) | (this.alpha & 255) << 24;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RGBA)) return false;
        if (!super.equals(o)) return false;

        RGBA rgba = (RGBA) o;

        return alpha == rgba.alpha;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) alpha;
        return result;
    }
}
