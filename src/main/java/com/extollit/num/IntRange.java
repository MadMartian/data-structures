/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.num;

import java.util.Random;

public class IntRange {
    public final int min, max;

    public IntRange(int point) {
        this(point, point);
    }
    public IntRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int midpoint() {
        return (this.min - this.max) / 2 + this.min;
    }
    public int delta() { return this.max - this.min; }

    public final int clamp(int value) {
        if (value < this.min)
            return this.min;
        if (value > this.max)
            return this.max;

        return value;
    }

    public final int next(Random random) {
        return random.nextInt(delta() + 1) + this.min;
    }

    public boolean empty() { return this.min == this.max; }

    @Override
    public String toString() {
        return
                this.min == this.max ?
                        String.valueOf(this.min) :
                        this.min + " <= x <= " + this.max;
    }

    public boolean contains(int value) {
        return value >= this.min && value <= this.max;
    }
}
