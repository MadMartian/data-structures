/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.Stack;

public class PreOrderTreeNodeIterator<T, Node extends AbstractNode<T, Node>> extends AbstractDFSTreeNodeIterator<T, Node> {
    public PreOrderTreeNodeIterator(IBinaryTree<T, Node> tree) {
        super(tree);
    }

    @Override
    protected Node findNext() {
        Node node = this.node;

        if (this.path == null) {
            this.path = new Stack<Arm>();
            this.path.push(Arm.top);

            return node;
        }

        while (!this.path.isEmpty()) {
            switch (this.path.pop()) {
                case top:
                    Node left = node.left();
                    if (left != null) {
                        this.path.push(Arm.left);
                        this.path.push(Arm.top);
                        return this.node = left;
                    }
                case left:
                    Node right = node.right();
                    if (right != null) {
                        this.path.push(Arm.right);
                        this.path.push(Arm.top);
                        return this.node = right;
                    }
                case right:
                    this.node = node = node.parent();
                    break;
            }
        }

        return null;
    }
}
