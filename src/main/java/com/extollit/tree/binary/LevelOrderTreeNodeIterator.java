/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LevelOrderTreeNodeIterator<T, Node extends AbstractNode<T, Node>> extends AbstractTreeNodeIterator<T, Node> {
    private List<Node> breadth;
    private Iterator<Node> biter;
    private Node current;
    private Arm arm;

    public LevelOrderTreeNodeIterator(IBinaryTree<T, Node> tree) {
        super(tree);
    }

    private Node add(Node node) {
        this.breadth.add(node);
        return node;
    }

    @Override
    protected Node findNext() {
        if (first()) {
            Node root = super.tree.root();
            this.breadth = new LinkedList<Node>();
            this.arm = Arm.top;
            return this.current = root;
        }

        do {
            Node current = this.current;

            if (current == null || this.arm == Arm.right) {
                if (this.biter == null || !this.biter.hasNext())
                    nextTier();

                if (!this.biter.hasNext())
                    return null;

                current = this.current = this.biter.next();
                this.arm = Arm.top;
            }

            Node left = current.left(),
                 right = current.right();

            if (this.arm == Arm.top)
                try {
                    if (left != null)
                        return add(left);
                } finally {
                    this.arm = Arm.left;
                }

            if (this.arm == Arm.left)
                try {
                    if (right != null)
                        return add(right);
                } finally {
                    this.arm = Arm.right;
                }
        } while(true);
    }

    private void nextTier() {
        this.biter = this.breadth.iterator();
        this.breadth = new LinkedList<Node>();
        this.arm = Arm.top;
    }

    @Override
    protected boolean first() {
        return this.breadth == null;
    }
}
