/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

public interface IBinaryTree<T, MyNode extends ITreeNode<T, MyNode>> {
    MyNode remove(ITreeNode<T, ?> node);
    MyNode root();
    MyNode root(MyNode newNode);
    MyNode createLeaf(T data);

    TreeNodeIterator<T, MyNode> inOrderIterator();
    TreeNodeIterator<T, MyNode> postOrderIterator();
}
