/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

public interface ITreeNode< T, MyNode extends ITreeNode<T, MyNode> > {
    T data();

    MyNode parent();
    MyNode left();
    MyNode right();

    void replaceWith(MyNode newNode);
}
