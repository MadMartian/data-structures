/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import java.util.Comparator;

public class CartesianTree< T > extends AbstractBinaryTree< T, Node<T> > implements IBinaryTree<T, Node<T>> {
    private final Comparator<T> comparator;

    private Node<T> previous;

    public CartesianTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T data) {
        Node<T> lesser = this.previous;
        while (lesser != null && this.comparator.compare(lesser.data(), data) >= 0)
            lesser = lesser.parent();

        this.previous = insertAfter(data, lesser);
    }

    public void subjugate(T data) {
        if (this.root == null)
            insert(data);
        else
            this.previous = insertAfter(data, this.previous);
    }

    public void append(T data) {
        if (this.root == null)
            insert(data);
        else
            insertAfter(data, this.previous);
    }

    public void uppend(T data) {
        if (this.root == null)
            insert(data);
        else
            insertUp(data, this.previous);
    }

    public void attach(CartesianTree<T> subTree) {
        attach(subTree.root);
    }

    private void attach(Node<T> node) {
        if (this.root == null) {
            this.root = node;
            this.previous = null;
            node.remove();
        } else {
            if (this.previous.right() != null)
                throw new IllegalStateException();

            this.previous.childRight(node);
        }
    }

    private Node<T> insertAfter(T data, Node<T> previous) {
        Node<T> newNode;
        if (previous == null)
            newNode = this.root = new Node<T>(data, this.root, null);
        else {
            newNode = previous.createChild(data);
            newNode.childLeft(previous.right());
            previous.childRight(newNode);
        }
        return newNode;
    }

    private Node<T> insertUp(T data, Node<T> proceeding) {
        Node<T> newNode;
        if (proceeding == null)
            newNode = this.root = new Node<T>(data, this.root, null);
        else {
            Node<T> up = proceeding.parent();

            if (up == null)
                newNode = this.root = new Node<T>(data, proceeding, null);
            else {
                newNode = up.createChild(data);
                proceeding.replaceWith(newNode);
                newNode.childLeft(proceeding);
            }
        }
        return newNode;
    }

    public ITreeNode<T, ? extends ITreeNode<T, ?>> previous() { return this.previous; }

    @Override
    public Node<T> createLeaf(T data) {
        return new Node<T>(data);
    }
}
