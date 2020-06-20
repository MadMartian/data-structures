package com.extollit.num;

/**
 * MadMartian Mod
 *
 * Created by MadMartian on 7/5/2015.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class GaussianFn implements ILinearFunction
{
    public final double a, b, c;

    private final double csq;

    public GaussianFn(double a, double b, double c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.csq = this.c*this.c;
    }

    @Override
    public double f(double x)
    {
        double alpha = x - b;
        return this.a * Math.exp(-(alpha*alpha)/(2 * this.csq));
    }
}
