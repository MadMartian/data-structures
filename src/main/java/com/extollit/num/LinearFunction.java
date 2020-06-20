package com.extollit.num;

public abstract class LinearFunction {
    public static double approximate(ILinearFunction function, ILinearFunction derivative, double x, double root, int iterations) {
        while (iterations-- > 0)
            x = x - (function.f(x) - root) / derivative.f(x);

        return x;
    }
}
