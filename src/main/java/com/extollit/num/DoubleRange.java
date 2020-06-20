/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.num;

import java.util.Random;

public class DoubleRange {
    public final double min, max;

    public DoubleRange(double point) {
        this(point, point);
    }
    public DoubleRange(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double midpoint() {
        return (this.min - this.max) / 2 + this.min;
    }
    public double ratio(double value) { return (value - this.min) / delta(); }
    public double delta() { return this.max - this.min; }

    public final double clamp(double value) {
        if (value < this.min)
            return this.min;
        if (value > this.max)
            return this.max;

        return value;
    }

    public final double next(Random random) {
        return random.nextDouble() * delta() + this.min;
    }

    public boolean empty() { return this.min == this.max; }

    @Override
    public String toString() {
        return
            this.min == this.max ?
                String.valueOf(this.min) :
                this.min + " <= x <= " + this.max;
    }

    public boolean contains(double value) {
        return value >= this.min && value <= this.max;
    }
}
