/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.num;

public class Range< T > {
    public static final Range< Double > PERCENTAGE = new Range<Double>(0.0, 1.0);

    public final T min, max;

    private final double dmin, dmax;

    public Range(T point) {
        this(point, point);
    }
    public Range(T min, T max) {
        this.min = min;
        this.max = max;

        this.dmin = ((Number)min).doubleValue();
        this.dmax = ((Number)max).doubleValue();
    }

    public double midpoint() {
        return (this.dmax - this.dmin) / 2 + this.dmin;
    }

    public double delta() { return this.dmax - this.dmin; }

    public final double clamp(double value) {
        if (value < this.dmin)
            return this.dmin;
        if (value > this.dmax)
            return this.dmax;

        return value;
    }

    public boolean empty() { return this.min.equals(this.max); }

    @Override
    public String toString() {
        return
                this.min == this.max ?
                        String.valueOf(this.min) :
                        this.min + " <= x <= " + this.max;
    }
}
