package com.extollit.linalg.immutable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AxisAlignedBBoxTests {
    @Test
    public void mg2InsideZMax() {
        final AxisAlignedBBox
            left = new AxisAlignedBBox(0, 0, 0, 1, 1, 1),
            right = new AxisAlignedBBox(0.8f, 0, 0.2f, 2, 0, 2);

        final double actual = Math.sqrt(left.mg2(right));

        assertEquals(0, actual, 0.001);
    }

    @Test
    public void mg2OutsideZMax() {
        final AxisAlignedBBox
                left = new AxisAlignedBBox(0, 0, 0, 1, 1, 1),
                right = new AxisAlignedBBox(1.2f, 0, 0.2f, 2, 0, 2);

        final double actual = Math.sqrt(left.mg2(right));

        assertEquals(0.2, actual, 0.001);
    }

    @Test
    public void mg2InsideZMin() {
        final AxisAlignedBBox
                left = new AxisAlignedBBox(0, 0, 0, 1, 1, 1),
                right = new AxisAlignedBBox(-1, 0, 0.2f, 0.3, 0, 2);

        final double actual = Math.sqrt(left.mg2(right));

        assertEquals(0, actual, 0.001);
    }

    @Test
    public void mg2OutsideZMin() {
        final AxisAlignedBBox
                left = new AxisAlignedBBox(0, 0, 0, 1, 1, 1),
                right = new AxisAlignedBBox(-2, 0, -0.2f, -1.2, 0, 2);

        final double actual = Math.sqrt(left.mg2(right));

        assertEquals(1.2, actual, 0.001);
    }

    @Test
    public void mg2OutsideXZ() {
        final AxisAlignedBBox
                left = new AxisAlignedBBox(0, 0, 0, 1, 1, 1),
                right = new AxisAlignedBBox(1.2, 0, 1.4, 5, 0, 8);

        final double actual = Math.sqrt(left.mg2(right));

        assertEquals(0.447, actual, 0.001);
    }


    @Test
    public void mg2TotallyInside() {
        final AxisAlignedBBox
                left = new AxisAlignedBBox(0, 0, 0, 1, 1, 1),
                right = new AxisAlignedBBox(0.2, 0.2, 0.2, 0.9, 0.9, 0.9);

        final double actual = Math.sqrt(left.mg2(right));

        assertEquals(0, actual, 0.001);
    }
}
