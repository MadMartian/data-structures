/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.test.property;

import java.util.Random;

/**
 * main
 *
 * Created by jonathan on 25/12/16.
 */
public final class EnumGen<E extends Enum<E>> implements IGenerator<E> {
    public final Class<E> clazz;

    private final E[] constants;

    public EnumGen(Class<E> clazz) {
        this.clazz = clazz;
        this.constants = clazz.getEnumConstants();
    }

    @Override
    public E next(Random random) {
        return constants[random.nextInt(constants.length)];
    }
}
