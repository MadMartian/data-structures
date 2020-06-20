package com.extollit.tuple;

/**
 * MadMartian Mod
 *
 * Created by MadMartian on 5/5/2015.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class Triple< A, B, C > {
    public final A first;
    public final B second;
    public final C third;

    public static class Sealed< A, B, C > {
        public final A first;
        public final B second;
        public final C third;

        public Sealed(A first, B second, C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public static < A, B, C > Sealed< A, B, C > of (A first, B second, C third) {
            return new Sealed< A, B, C >(first, second, third);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Sealed triple = (Sealed) o;

            if (!first.equals(triple.first)) return false;
            if (!second.equals(triple.second)) return false;
            return third.equals(triple.third);

        }

        @Override
        public int hashCode() {
            int result = first.hashCode();
            result = 31 * result + second.hashCode();
            result = 31 * result + third.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return '(' + this.first.toString() + ',' + this.second.toString() + ',' + this.third.toString() + ')';
        }
    }

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Sealed<A, B, C> sealed() { return new Sealed<A, B, C>(this.first, this.second, this.third); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple triple = (Triple) o;

        if (!first.equals(triple.first)) return false;
        if (!second.equals(triple.second)) return false;
        return third.equals(triple.third);

    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        result = 31 * result + third.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return '(' + this.first.toString() + ',' + this.second.toString() + ',' + this.third.toString() + ')';
    }

}
