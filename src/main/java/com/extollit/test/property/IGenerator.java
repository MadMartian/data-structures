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
public interface IGenerator<T> {
    T next(Random random);
}
