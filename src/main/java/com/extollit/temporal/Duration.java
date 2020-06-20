/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.temporal;

public final class Duration
{
    public enum Units {
        milliseconds(1),
        seconds(1000),
        minutes(1000 * 60),
        hours(1000 * 60 * 60),
        days(1000 * 60 * 60 * 24);

        public final long millis;

        Units(int millis) {
            this.millis = millis;
        }

        public static Units mostRefined(Units left, Units right) {
            if (left.millis < right.millis)
                return left;
            else
                return right;
        }
        public static Units mostRefined(long millis) {
            Units[] values = values();
            for (int c = values.length - 1; c >= 0; --c) {
                Units unit = values[c];
                if (millis % unit.millis == 0)
                    return unit;
            }

            return milliseconds;
        }
    }

    public final long value;
    public final Units units;

    public Duration(long value, Units timeUnit) {
        this.units = timeUnit;
        this.value = value;
    }
    public static Duration of(long value, Units timeUnit)
    {
        return new Duration(value, timeUnit);
    }
    public static Duration of (Duration other, Units timeUnit)
    {
        return new Duration(other.value * other.units.millis / timeUnit.millis, timeUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duration)) return false;

        Duration duration = (Duration) o;

        if (value != duration.value) return false;
        return units == duration.units;

    }

    @Override
    public int hashCode() {
        int result = (int) (value ^ (value >>> 32));
        result = 31 * result + units.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.value + " " + this.units;
    }

    public long millis() {
        return this.units.millis * this.value;
    }
}
