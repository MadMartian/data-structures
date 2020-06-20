/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.text.MessageFormat;

abstract class AbstractNode<T, MyNode extends AbstractNode<T, MyNode>> implements ITreeNode<T, MyNode> {
    T data;

    private MyNode up, left, right;

    AbstractNode(T data) {
        this(null, data);
    }

    public AbstractNode(MyNode up, T data) {
        this.data = data;
        this.up = up;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNode)) return false;

        AbstractNode<?, ?> that = (AbstractNode<?, ?>) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        return !(right != null ? !right.equals(that.right) : that.right != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    @Override
    public MyNode parent() { return this.up; }

    MyNode rightRight() {
        return this.right == null ? null : this.right.right();
    }

    MyNode leftLeft() {
        return this.left == null ? null : this.left.left();
    }

    MyNode rightLeft() {
        return this.right == null ? null : this.right.left();
    }

    MyNode leftRight() {
        return this.left == null ? null : this.left.right();
    }

    abstract MyNode createChild(T data);
    abstract MyNode createChild(T data, MyNode left, MyNode right);

    @Override
    public void replaceWith(MyNode newNode) {
        if (this.up != null)
            if (this.up.left() == this)
                this.up.childLeft(newNode);
            else if (this.up.right() == this)
                this.up.childRight(newNode);
            else
                throw new IllegalStateException(
                    MessageFormat.format("Inconsistent state, this node {0} is not a child of {1} while replacing with {2}", this, this.up, newNode)
                );
        else
            newNode.remove();
    }

    protected void remove() {
        if (this.up != null && this.up.left() == this)
            this.up.acceptLeftChild(null);
        if (this.up != null && this.up.right() == this)
            this.up.acceptRightChild(null);

        this.up = null;
    }

    protected void acceptLeftChild(MyNode child) {
        this.left = child;
    }

    protected void acceptRightChild(MyNode child) {
        this.right = child;
    }

    protected void acceptParent(MyNode parent) {
        this.up = parent;
    }

    @SuppressWarnings("unchecked")
    protected void parentLeft(MyNode parent) {
        this.up = parent;
        parent.acceptLeftChild((MyNode) this);
    }

    @SuppressWarnings("unchecked")
    protected void parentRight(MyNode parent) {
        this.up = parent;
        parent.acceptRightChild((MyNode) this);
    }

    @SuppressWarnings("unchecked")
    protected void childRight(MyNode child) {
        this.right = child;
        if (child != null)
            child.acceptParent((MyNode) this);
    }

    @SuppressWarnings("unchecked")
    protected void childLeft(MyNode child) {
        this.left = child;
        if (child != null)
            child.acceptParent((MyNode) this);
    }

    void rotateLeft() {
        this.left = createChild(this.data, this.left, rightLeft());
        this.data = this.right.data();
        this.childRight(rightRight());
    }

    void rotateRight() {
        this.right = createChild(this.data, this.right, leftRight());

        this.data = this.left.data();
        this.childLeft(leftLeft());
    }

    @Override
    public T data() {
        return this.data;
    }

    @Override
    public MyNode left() {
        return this.left;
    }

    @Override
    public MyNode right() {
        return this.right;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    @Override
    public String toString() {
        return this.data == null ? "null" : this.data.toString();
    }
}

class Node<T> extends AbstractNode<T, Node<T>> {
    Node(T data) {
        super(data);
    }

    Node(Node<T> up, T data) {
        super(up, data);
    }

    Node(Node<T> up, T data, Node<T> left, Node<T> right) {
        super(up, data);
        childLeft(left);
        childRight(right);
    }

    Node(T data, Node<T> left, Node<T> right) {
        super(data);
        childLeft(left);
        childRight(right);
    }

    @Override
    Node<T> createChild(T data) {
        return new Node<T>(this, data);
    }

    @Override
    Node<T> createChild(T data, Node<T> left, Node<T> right) {
        return new Node<T>(this, data, left, right);
    }
}

