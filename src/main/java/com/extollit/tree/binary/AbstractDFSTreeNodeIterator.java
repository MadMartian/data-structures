/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.Stack;

abstract class AbstractDFSTreeNodeIterator<T, Node extends AbstractNode<T, Node>> extends AbstractTreeNodeIterator<T,Node> {
    protected Stack<Arm> path;

    protected AbstractDFSTreeNodeIterator(IBinaryTree<T, Node> tree) {
        super(tree);
    }

    @Override
    protected boolean first() {
        return this.path == null;
    }

}
