/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.num;

import java.util.Random;

public class FloatRange {
    public final float min, max;

    public FloatRange(float point) {
        this(point, point);
    }
    public FloatRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float midpoint() {
        return (this.min - this.max) / 2 + this.min;
    }
    public float ratio(float value) { return (value - this.min) / delta(); }
    public float delta() { return this.max - this.min; }

    public final float clamp(float value) {
        if (value < this.min)
            return this.min;
        if (value > this.max)
            return this.max;

        return value;
    }

    public final float next(Random random) {
        return random.nextFloat() * delta() + this.min;
    }

    public boolean empty() { return this.min == this.max; }

    @Override
    public String toString() {
        return
            this.min == this.max ?
                String.valueOf(this.min) :
                this.min + " <= x <= " + this.max;
    }

    public boolean contains(float value) {
        return value >= this.min && value <= this.max;
    }
}
