/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.linalg.mutable.Vec3i;

/**
 * main
 *
 * Created by jonathan on 22/02/17.
 */
interface IOctantOperation<T> {
    void child(Octant<T> parent, int parity, Vec3i mp, int scale);
}
