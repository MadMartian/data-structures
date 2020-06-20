package com.extollit.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PermutationsTests {
    @Test
    public void triple() {
        Assert.assertEquals(
                Arrays.asList(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(1, 3, 2),
                        Arrays.asList(2, 1, 3),
                        Arrays.asList(2, 3, 1),
                        Arrays.asList(3, 1, 2),
                        Arrays.asList(3, 2, 1)
                ),
                CollectionsExt.toList(new PermutationsIterator<Integer>(Arrays.asList(1, 2, 3)))
        );
    }

    @Test
    public void quadruple() {
        Assert.assertEquals(
                Arrays.asList(
                        Arrays.asList(1, 2, 3, 4),
                        Arrays.asList(1, 2, 4, 3),
                        Arrays.asList(1, 3, 2, 4),
                        Arrays.asList(1, 3, 4, 2),
                        Arrays.asList(1, 4, 2, 3),
                        Arrays.asList(1, 4, 3, 2),
                        Arrays.asList(2, 1, 3, 4),
                        Arrays.asList(2, 1, 4, 3),
                        Arrays.asList(2, 3, 1, 4),
                        Arrays.asList(2, 3, 4, 1),
                        Arrays.asList(2, 4, 1, 3),
                        Arrays.asList(2, 4, 3, 1),
                        Arrays.asList(3, 1, 2, 4),
                        Arrays.asList(3, 1, 4, 2),
                        Arrays.asList(3, 2, 1, 4),
                        Arrays.asList(3, 2, 4, 1),
                        Arrays.asList(3, 4, 1, 2),
                        Arrays.asList(3, 4, 2, 1),
                        Arrays.asList(4, 1, 2, 3),
                        Arrays.asList(4, 1, 3, 2),
                        Arrays.asList(4, 2, 1, 3),
                        Arrays.asList(4, 2, 3, 1),
                        Arrays.asList(4, 3, 1, 2),
                        Arrays.asList(4, 3, 2, 1)
                    ),
                CollectionsExt.toList(new PermutationsIterator<Integer>(Arrays.asList(1, 2, 3, 4)))
        );
    }
}
