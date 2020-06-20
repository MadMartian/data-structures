/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.num;

/**
 * main
 * <p/>
 * Created by jonathan on 25/12/16.
 */
public class FastMath {
    public static int abs(int value) {
        final int mask = value >> (Integer.SIZE - 1);

        value ^= mask;
        value += mask & 1;
        return value;
    }

    public static int floor(double f) {
        final int i = (int) f;
        return f < i ? i - 1 : i;
    }

    public static int floor(float f) {
        final int i = (int) f;
        return f < i ? i - 1 : i;
    }

    public static int ceil(double f) {
        final int i = (int) f;
        return f > i ? i + 1 : i;
    }

    public static int ceil(float f) {
        final int i = (int) f;
        return f > i ? i + 1 : i;
    }
}
