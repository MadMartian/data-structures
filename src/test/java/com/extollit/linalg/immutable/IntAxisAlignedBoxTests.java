/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.linalg.immutable;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * main
 * <p/>
 * Created by jonathan on 16/12/16.
 */
public class IntAxisAlignedBoxTests {

    private void assertThat(boolean expectation, IntAxisAlignedBox left, IntAxisAlignedBox right) {
        boolean
            lr = left.intersects(right),
            rr = right.intersects(left);

        assertFalse("does not commute", lr ^ rr);
        if (expectation)
            assertTrue("does not intersect", lr);
        else
            assertFalse("intersects", lr);
    }

    @Test
    public void basicIntersects() {
        assertThat(true, new IntAxisAlignedBox(0, 0, 0, 10, 10, 10), new IntAxisAlignedBox(5, 5, 5, 15, 15, 15));
        assertThat(true, new IntAxisAlignedBox(0, 0, 0, 10, 10, 10), new IntAxisAlignedBox(2, 2, 2, 6, 6, 6));
        assertThat(true, new IntAxisAlignedBox(-5, -5, -5, 0, 0, 0), new IntAxisAlignedBox(-2, -2, -2, 6, 6, 6));
        assertThat(true, new IntAxisAlignedBox(-5, -5, -5, 0, 0, 0), new IntAxisAlignedBox(0, 0, 0, 5, 5, 5));

        assertThat(false, new IntAxisAlignedBox(0, 0, 0, 10, 10, 10), new IntAxisAlignedBox(11, 11, 11, 15, 15, 15));
        assertThat(false, new IntAxisAlignedBox(-5, -5, -5, 0, 0, 0), new IntAxisAlignedBox(2, 2, 2, 6, 6, 6));
    }

    @Test
    public void complexIntersects() {
        assertThat(true, new IntAxisAlignedBox(0, -4, 70, 10, -2, 72), new IntAxisAlignedBox(5, -2, 71, 15, 1, 73));
        assertThat(true, new IntAxisAlignedBox(-5, 5, -10, -5, 5, -10), new IntAxisAlignedBox(-5, 5, -10, 0, 5, 10));

        assertThat(true, new IntAxisAlignedBox(-5, 5, -10, -5, 5, 10), new IntAxisAlignedBox(-5, 5, -10, -5, 5, 10));
        assertThat(true, new IntAxisAlignedBox(-5, 5, -10, 5, 15, 20), new IntAxisAlignedBox(-5, 5, -10, 5, 15, 20));

        assertThat(false, new IntAxisAlignedBox(-5, 5, -10, -2, 15, 0), new IntAxisAlignedBox(-5, 20, -10, 0, 25, 10));
        assertThat(false, new IntAxisAlignedBox(-5, 5, -10, 5, 15, 20), new IntAxisAlignedBox(6, 5, -10, 6, 15, 20));
    }

    @Test
    public void arbitrary1() {
        assertThat(true, new IntAxisAlignedBox(-287, Integer.MIN_VALUE >> 1, -260, 225, Integer.MAX_VALUE >> 1, 252), new IntAxisAlignedBox(-13, -1, 4, 7, 19, 24));
    }
}
