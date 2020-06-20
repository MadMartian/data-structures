package com.extollit.collect;

/**
 * MadMartian Mod
 * <p/>
 * Created by jonathan on 05/01/16.
 * <p/>
 * Copyright (c) 2015 extollIT Enterprises
 * <p/>
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public interface IXFormer< Out, In > {
    Out transform(In element);
}
