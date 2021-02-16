package com.extollit.num;

import static java.lang.Math.floor;

public class CachedLinearFunction< F extends ILinearFunction > implements ILinearFunction {
    public final F host;
    public final float min, max, step;

    private final float [] cacheFunc;

    public CachedLinearFunction(F host, float min, float max, float step) {
        this.host = host;
        this.min = min;
        this.max = max;
        this.step = step;

        double i = min;
        int c = 0;
        final int length = (int) Math.ceil((max - min) / step) + 1;
        final float []
                cacheFunc = new float[length];

        while (i <= max) {
            cacheFunc[c] = (float)host.f(i);
            c++;
            i += step;
        }
        this.cacheFunc = cacheFunc;
    }

    @Override
    public final double f(double x) {
        return this.cacheFunc[(int)floor((clamp(x) - this.min) / this.step)];
    }

    protected double clamp(double x) {
        if (x < this.min)
            x = this.min;
        if (x > this.max)
            x = this.max;

        return x;
    }
}
