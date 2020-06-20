/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

abstract class AbstractBinaryTree<T, MyNode extends AbstractNode<T, MyNode>> implements IBinaryTree<T,MyNode> {
    protected MyNode root;

    @Override
    public final MyNode root() {
        return this.root;
    }

    @Override
    public MyNode root(MyNode newNode) {
        MyNode root0 = this.root;
        this.root = newNode;
        newNode.acceptParent(null);
        return root0;
    }

    @Override
    public MyNode remove(ITreeNode<T, ?> node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final TreeNodeIterator<T, MyNode> inOrderIterator() {
        return new InOrderTreeNodeIterator<T, MyNode>(this);
    }

    @Override
    public final TreeNodeIterator<T, MyNode> postOrderIterator() {
        return new PostOrderTreeNodeIterator<T, MyNode>(this);
    }

    public final TreeNodeIterator<T, MyNode> preOrderIterator() {
        return new PreOrderTreeNodeIterator<T, MyNode> (this);
    }

    public final TreeNodeIterator<T, MyNode> levelOrderIterator() {
        return new LevelOrderTreeNodeIterator<T, MyNode> (this);
    }

    public boolean singleton() {
        return this.root != null && this.root.isLeaf();
    }
}
