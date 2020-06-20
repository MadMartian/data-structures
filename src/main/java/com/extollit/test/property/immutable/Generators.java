/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.test.property.immutable;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.immutable.Vec3i;
import com.extollit.test.property.IGenerator;

import java.util.Random;

/**
 * main
 * <p/>
 * Created by jonathan on 25/12/16.
 */
public class Generators {
    public static final class IntAxisAlignedBoxGen implements IGenerator<IntAxisAlignedBox> {
        private final Vec3iGen ming, maxg;

        public IntAxisAlignedBoxGen(Vec3i mp, int range) {
            final int halfRange = range / 2;
            this.ming = new Vec3iGen(mp.subOf(halfRange), halfRange);
            this.maxg = new Vec3iGen(mp.plusOf(halfRange), halfRange);
        }

        @Override
        public IntAxisAlignedBox next(Random random) {
            return new IntAxisAlignedBox(
                this.ming.next(random),
                this.maxg.next(random)
            );
        }
    }

    public static final class Vec3iGen implements IGenerator<Vec3i> {
        private final Vec3i mp;
        private final int range;

        public Vec3iGen(Vec3i mp, int range) {
            this.mp = mp;
            this.range = range;
        }

        @Override
        public Vec3i next(Random random) {
            final int dr = range - range / 2;
            final int dx = random.nextInt(dr),
                      dy = random.nextInt(dr),
                      dz = random.nextInt(dr);

            return mp.plusOf(new Vec3i(dx, dy, dz));
        }
    }
}
