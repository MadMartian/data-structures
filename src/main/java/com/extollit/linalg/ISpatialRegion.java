/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg;

import com.extollit.linalg.immutable.AxisAlignedBBox;
import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.immutable.Sphere;
import com.extollit.linalg.immutable.Vec2d;

/**
 * main
 *
 * Created by jonathan on 12/09/16.
 */
public interface ISpatialRegion {
    boolean contains(double x, double y, double z);
    boolean contains(com.extollit.linalg.immutable.Vec3d coordinates);
    boolean contains(com.extollit.linalg.mutable.Vec3d coordinates);
    boolean contains(int x, int y, int z);
    boolean contains(com.extollit.linalg.immutable.Vec3i coordinates);
    boolean contains(com.extollit.linalg.mutable.Vec3i coordinates);

    interface Visitor {
        void visit(com.extollit.linalg.immutable.Vec3i vec);
        void visit(com.extollit.linalg.mutable.Vec3i vec);
        void visit(com.extollit.linalg.immutable.Vec3d vec);
        void visit(com.extollit.linalg.mutable.Vec3d vec);

        void visit(AxisAlignedBBox box);
        void visit(com.extollit.linalg.mutable.AxisAlignedBBox box);

        void visit(IntAxisAlignedBox box);
        void visit(Sphere sphere);

        void visit(Vec2d vec2d);
        void visit(com.extollit.linalg.mutable.Vec2d vec2d);
    }

    void accept(Visitor visitor);
}
