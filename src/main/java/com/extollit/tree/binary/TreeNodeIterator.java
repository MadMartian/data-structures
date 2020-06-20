/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.Iterator;

public interface TreeNodeIterator<T, N extends ITreeNode<T, N>> extends Iterator<N> {
    N pruneWith(T data);
    T changeTo(T data);
}
