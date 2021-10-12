/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.test.property.IGenerator;
import com.extollit.test.property.immutable.Generators;

import java.util.Random;

/**
 * main
 *
 * Created by jonathan on 25/12/16.
 */
public class OctTreeEntryGen<T> implements IGenerator<OctTree.Entry<T>> {
    private final Generators.IntAxisAlignedBoxGen boxGen;
    private final IGenerator<T> valueGen;

    public OctTreeEntryGen(Generators.IntAxisAlignedBoxGen boxGen, IGenerator<T> valueGen) {
        this.boxGen = boxGen;
        this.valueGen = valueGen;
    }

    @Override
    public OctTree.Entry<T> next(Random random) {
        return new OctTree.Entry<T>(
            boxGen.next(random),
            valueGen.next(random)
        );
    }
}
