/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg;

import com.extollit.linalg.immutable.Vec3d;
import com.extollit.linalg.mutable.Vec3i;

/**
 * main
 * <p/>
 * Created by jonathan on 12/09/16.
 */
public abstract class AbstractSpatialRegion implements ISpatialRegion {
    @Override
    public final boolean contains(Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public final boolean contains(com.extollit.linalg.mutable.Vec3d coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(Vec3i coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(com.extollit.linalg.immutable.Vec3i coordinates) {
        return contains(coordinates.x, coordinates.y, coordinates.z);
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return contains((double)x, (double)y, (double)z);
    }
}
