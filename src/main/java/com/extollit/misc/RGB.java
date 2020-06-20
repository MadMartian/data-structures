package com.extollit.misc;

import com.extollit.num.IntRange;

/**
 * MadMartian Mod
 *
 * Created by MadMartian on 3/23/2015.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class RGB {
    private static final IntRange COMPONENT_RANGE = new IntRange(0, 255);

    public static final RGB
        WHITE = new RGB(1.0f, 1.0f, 1.0f),
        DARK_BLUE = new RGB(0.0f, 0.0f, 0.5f),
        BLUE = new RGB(0.0f, 0.0f, 1.0f),
        GRAY = new RGB(0.5f, 0.5f, 0.5f),
        LIGHT_GRAY = new RGB(0.8f, 0.8f, 0.8f),
        DARK_GRAY = new RGB(0.125f, 0.125f, 0.125f),
        RED = new RGB(1.0f, 0.0f, 0.0f),
        BLACK = new RGB(0, 0, 0),
        YELLOW = new RGB(1.0f, 1.0f, 0),
        GREEN = new RGB(0, 1.0f, 0),
        PURPLE = new RGB(0.5f, 0, 1f);

    public short red;
    public short green;
    public short blue;

    public static short toShort(float f)
    {
        return (short) COMPONENT_RANGE.clamp((short)(f * 255.0f));
    }
    protected static float toFloat(short c)
    {
        return (float)COMPONENT_RANGE.clamp(c) / 255.0f;
    }

    public RGB(float red, float green, float blue) {
        this.red = toShort(red);
        this.green = toShort(green);
        this.blue = toShort(blue);
    }

    private RGB(short red, short green, short blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public RGB(int integer) {
        this.red = (short)(integer >> 16 & 255);
        this.green = (short)(integer >> 8 & 255);
        this.blue = (short)(integer & 255);
    }

    public static RGB fromHSL(float hue, float sat, float light) {
        final float q = light < 0.5 ? light * (1 + sat) : light + sat - light * sat;
        final float p = 2 * light - q;

        return new RGB(
            channelFromHSL(p, q, hue + 1f/3),
            channelFromHSL(p, q, hue),
            channelFromHSL(p, q, hue - 1f/3)
        );
    }
    private static float channelFromHSL(float p, float q, float t) {
        if (t < 0)
            t += 1;
        if (t > 1)
            t -= 1;
        if (t < 1f/6)
            return p + (q - p) * 6 * t;
        if (t < 1f/2)
            return q;
        if (t < 2f/3)
            return p + (q - p) * (2f/3 - t) * 6;
        return p;
    }

    public HSL toHSL() {
        float
            r = fred(),
            g = fgreen(),
            b = fblue();

        float max = r;
        if (g > max)
            max = g;
        if (b > max)
            max = b;
        float min = r;
        if (g < min)
            min = g;
        if (b < min)
            min = b;

        final float chroma = max - min;
        float hue = 0;
        if (chroma != 0) {
            if (max == r)
                hue = ((g - b) / chroma) % 6;
            else if (max == g)
                hue = ((b - r) / chroma) + 2;
            else if (max == b)
                hue = ((r - g) / chroma) + 4;
        }

        final float lightness = (max + min) / 2;

        return new HSL(
            hue,
            chroma == 0 ? 0 : chroma / (1 - Math.abs(2 * lightness - 1)),
            lightness
        );
    }

    public float fred() { return toFloat(this.red); }
    public float fgreen() { return toFloat(this.green); }
    public float fblue() { return toFloat(this.blue); }

    public void set(float red, float blue, float green)
    {
        this.red = toShort(red);
        this.blue = toShort(blue);
        this.green = toShort(green);
    }

    protected static short scale(short color, float f)
    {
        return (short)COMPONENT_RANGE.clamp((int)(((float)color) * f));
    }

    protected static short brighten(short color, float f)
    {
        return (short)COMPONENT_RANGE.clamp((int)(color + (f * 255f)));
    }

    public RGB brighten(float f) {
        this.red = brighten(this.red, f);
        this.green = brighten(this.green, f);
        this.blue = brighten(this.blue, f);
        return this;
    }

    public RGB scale(float f) {
        this.red = scale(this.red, f);
        this.green = scale(this.green, f);
        this.blue = scale(this.blue, f);
        return this;
    }

    public RGB brightened(float f) {
        return new RGB(
            brighten(this.red, f),
            brighten(this.green, f),
            brighten(this.blue, f)
        );
    }

    public RGB scaled(float f) {
        return new RGB(
            scale(this.red, f),
            scale(this.green, f),
            scale(this.blue, f)
        );
    }

    public int toInt() {
        return
                (0xFF << 24) |
                ((COMPONENT_RANGE.clamp(red) & 255) << 16) |
                ((COMPONENT_RANGE.clamp(green) & 255) << 8) |
                ((COMPONENT_RANGE.clamp(blue) & 255));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RGB)) return false;

        RGB rgb = (RGB) o;

        if (red != rgb.red) return false;
        if (green != rgb.green) return false;
        return blue == rgb.blue;

    }

    @Override
    public int hashCode() {
        int result = (int) red;
        result = 31 * result + (int) green;
        result = 31 * result + (int) blue;
        return result;
    }

    @Override
    public String toString() {
        return Integer.toString(toInt(), 16);
    }

    public static final RGB blend(RGB a, RGB b, float amount) {
        final float inv = 1 - amount;
        return new RGB(
            (short)(a.red * inv + b.red * amount),
            (short)(a.green * inv + b.green * amount),
            (short)(a.blue * inv + b.blue * amount)
        );
    }
}
