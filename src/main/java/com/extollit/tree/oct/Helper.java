/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.mutable.Vec3i;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
class Helper {
    private static final double LOG2 = Math.log(2);

    private static final int
            TOP_BITNUM = (Integer.SIZE - 1),
            TOP_BIT = 1 << TOP_BITNUM;

    static final int TOP_SCALE = (TOP_BIT >> 1) ^ TOP_BIT;

    static int scale(IntAxisAlignedBox key) {
        return 1 << (int)Math.ceil(Math.log(key.max.subOf(key.min).magnitude()) / LOG2);
    }

    static byte parity(int value, int mp) {
        return (byte)((((mp - value) & TOP_BIT) >> TOP_BITNUM) & 1);
    }

    static byte parity(Vec3i value, Vec3i mp) {
        return (byte)(parity(value.x, mp.x) | (parity(value.y, mp.y) << 1) | (parity(value.z, mp.z) << 2));
    }

    static byte parity(com.extollit.linalg.immutable.Vec3i value, Vec3i mp) {
        return (byte)(parity(value.x, mp.x) | (parity(value.y, mp.y) << 1) | (parity(value.z, mp.z) << 2));
    }
}
