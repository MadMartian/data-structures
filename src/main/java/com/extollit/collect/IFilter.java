package com.extollit.collect;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 10/01/16.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public interface IFilter< In > {
    boolean test(In element);
}
