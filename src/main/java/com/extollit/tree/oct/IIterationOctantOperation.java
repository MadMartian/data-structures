/*
 * MadMartian Mod - Copyright (c) 2017 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import java.util.List;

/**
 * main
 * <p/>
 * Created by jonathan on 22/02/17.
 */
interface IIterationOctantOperation<T> extends IOctantOperation<T> {
    List<OctTree.Entry<T>> next();
}
