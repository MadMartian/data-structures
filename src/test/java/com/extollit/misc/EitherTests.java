/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.misc;

import junit.framework.Assert;
import org.junit.Test;

/**
 * main
 * <p/>
 * Created by jonathan on 05/12/16.
 */
public class EitherTests {
    @Test
    public void projectionLeft() {
        Assert.assertEquals(Either.Projection.LEFT, Either.left("Bob").projection());
    }

    @Test
    public void projectionRight() {
        Assert.assertEquals(Either.Projection.RIGHT, Either.right("Bob").projection());
    }
}
