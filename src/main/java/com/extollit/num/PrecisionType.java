package com.extollit.num;

import java.text.MessageFormat;

/**
 * MadMartian Mod
 * <p/>
 * Created by jonathan on 12/02/16.
 * <p/>
 * Copyright (c) 2016 extollIT Enterprises
 * <p/>
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class PrecisionType {
    public static final PrecisionType INTEGER = new PrecisionType(0);

    public final double epsilon, epsq;

    private final String fmt;

    public PrecisionType(int precision) {
        this.epsilon = 
        this.epsq = Math.pow(10.0, -precision);

        StringBuilder sbfmt = new StringBuilder("{0,number,#.");
        while (precision-- > 0)
            sbfmt.append('#');
        sbfmt.append('}');
        this.fmt = sbfmt.toString();
    }

    public double round(double value) {
        return ((double)Math.round(value / epsilon)) * epsilon;
    }
    public double roundOff(int places, double value) {
        double offset = Math.pow(10.0, places);
        return round(value / offset) * offset;
    }
    public double ceil(double value) {
        return ((double)Math.ceil(value / epsilon)) * epsilon;
    }
    public double floor(double value) {
        return ((double)Math.floor(value / epsilon)) * epsilon;
    }

    public boolean equal(double left, double right)
    {
        final double absA = Math.abs(left);
        final double  absB = Math.abs(right);
        final double diff = Math.abs(left - right);

        boolean ulp0 = diff <= epsilon;
        if (left == right || left == 0 || right == 0 || ulp0) {
            return ulp0;
        } else { // use relative error
            return diff / Math.min((absA + absB), Double.MAX_VALUE) <= epsilon;
        }
    }

    public boolean nonZero(double value)
    {
        return ((double)value)*((double)value) > epsq;
    }
    
    public String toString(double value)
    {
        if (value == Double.POSITIVE_INFINITY)
            return "+∞";
        else if (value == Double.NEGATIVE_INFINITY)
            return "-∞";
        else if (Double.isNaN(value))
            return "NaN";

        return MessageFormat.format(this.fmt, value);
    }
}
