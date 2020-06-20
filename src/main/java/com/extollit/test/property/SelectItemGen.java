/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.test.property;

import java.util.Random;

/**
 * main
 * <p/>
 * Created by jonathan on 25/12/16.
 */
public class SelectItemGen<T> implements IGenerator<T> {
    private final T[] items;

    public SelectItemGen(T... items) {
        this.items = items;
    }

    @Override
    public T next(Random random) {
        return items[random.nextInt(items.length)];
    }
}
