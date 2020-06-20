/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.Stack;

public class PostOrderTreeNodeIterator<T, Node extends AbstractNode<T, Node>> extends AbstractDFSTreeNodeIterator<T, Node> {
    public PostOrderTreeNodeIterator(IBinaryTree<T, Node> tree) {
        super(tree);
    }

    @Override
    protected Node findNext() {
        Node node = this.node;

        if (this.path == null) {
            this.path = new Stack<Arm>();
            return descend(node);
        }

        while (!this.path.empty()) {
            Arm arm = this.path.pop();
            switch (arm) {
                case top:
                    node = node.parent();
                    break;

                case left:
                    this.path.push(Arm.right);
                    Node right = node.right();
                    if (right != null)
                        return descend(right);
                    else
                        break;

                case right:
                    this.path.push(Arm.top);
                    return node;
            }
        }

        return null;
    }

    private Node descend(Node node) {
        do {
            while (node.left() != null) {
                this.path.push(Arm.left);
                node = node.left();
            }
            if (node.right() != null) {
                this.path.push(Arm.right);
                node = node.right();
            } else
                break;
        } while (true);

        this.path.push(Arm.top);
        return node;
    }
}
