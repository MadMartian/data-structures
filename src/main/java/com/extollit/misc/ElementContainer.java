package com.extollit.misc;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 09/01/16.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public final class ElementContainer < T > {
    public T element;

    public ElementContainer() {}
    public ElementContainer(T element) { this.element = element; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElementContainer)) return false;

        ElementContainer<?> that = (ElementContainer<?>) o;

        return !(element != null ? !element.equals(that.element) : that.element != null);

    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "( " + String.valueOf(this.element) + " )";
    }

    public boolean empty() {
        return this.element == null;
    }
}
