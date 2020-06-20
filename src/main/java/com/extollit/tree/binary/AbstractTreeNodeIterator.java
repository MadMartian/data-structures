/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.NoSuchElementException;

abstract class AbstractTreeNodeIterator<T, Node extends AbstractNode<T, Node>> implements TreeNodeIterator<T, Node> {
    protected enum Arm {
        left, top, right
    }

    protected final IBinaryTree<T, Node> tree;
    protected Node node;
    protected Node node0;

    public AbstractTreeNodeIterator(IBinaryTree<T, Node> tree) {
        this.node = tree.root();
        this.tree = tree;
    }

    protected abstract Node findNext();

    @Override
    public boolean hasNext() {
        return this.node != null;
    }

    private void moveNext() {
        this.node0 = this.node;
        this.node = findNext();
    }

    @Override
    public Node next() {
        if (first())
            this.node = findNext();

        Node current = this.node;
        moveNext();

        if (current == null)
            throw new NoSuchElementException();

        return current;
    }

    @Override
    public void remove() {
        if (this.node0 == null)
            throw new NoSuchElementException();

        this.tree.remove(this.node0);
    }

    @Override
    public Node pruneWith(T data) {
        if (this.node0 == null)
            throw new NoSuchElementException();

        Node newNode = this.tree.createLeaf(data);

        if (this.node0 == this.tree.root())
            this.tree.root(newNode);
        else
            this.node0.replaceWith(newNode);

        return this.node0;
    }

    @Override
    public T changeTo(T data) {
        if (this.node0 == null)
            throw new NoSuchElementException();

        T data0 = this.node0.data;
        this.node0.data = data;
        return data0;
    }

    protected abstract boolean first();

}
