/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.test.property;

import com.extollit.num.IntRange;

import java.util.Collection;
import java.util.Random;

/**
 * main
 * <p/>
 * Created by jonathan on 25/12/16.
 */
abstract class AbstractCollectionGenerator<T, C extends Collection<T>> implements IGenerator<C> {
    public final IGenerator<T> elementGenerator;
    public final IntRange quantityRange;

    protected AbstractCollectionGenerator(IGenerator<T> elementGenerator, IntRange quantityRange) {
        this.elementGenerator = elementGenerator;
        this.quantityRange = quantityRange;
    }

    @Override
    public C next(Random random) {
        final int quantity = this.quantityRange.next(random);
        final C result = createCollection(quantity);

        for (int c = 0; c < quantity; ++c)
            result.add(this.elementGenerator.next(random));

        return result;
    }

    protected abstract C createCollection(int capacity);
}
