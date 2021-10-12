package com.extollit.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MadMartian Mod
 *
 * Created by jonathann on 15-11-19.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class CombinationsTests {
    @Test
    public void basic3_4() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        Assert.assertEquals(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 4),
                Arrays.asList(1, 3, 4),
                Arrays.asList(2, 3, 4)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).tupleCombinations(3))
        );
    }

    @Test
    public void basic3_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 4),
                Arrays.asList(1, 2, 5),
                Arrays.asList(1, 3, 4),
                Arrays.asList(1, 3, 5),
                Arrays.asList(1, 4, 5),
                Arrays.asList(2, 3, 4),
                Arrays.asList(2, 3, 5),
                Arrays.asList(2, 4, 5),
                Arrays.asList(3, 4, 5)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).tupleCombinations(3))
        );
    }

    @Test
    public void basic5_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).tupleCombinations(5))
        );
    }


    @Test
    public void basic1_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Collections.singletonList(1),
                Collections.singletonList(2),
                Collections.singletonList(3),
                Collections.singletonList(4),
                Collections.singletonList(5)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).tupleCombinations(1))
        );
    }


    @Test
    public void basic2_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(1, 3),
                Arrays.asList(1, 4),
                Arrays.asList(1, 5),
                Arrays.asList(2, 3),
                Arrays.asList(2, 4),
                Arrays.asList(2, 5),
                Arrays.asList(3, 4),
                Arrays.asList(3, 5),
                Arrays.asList(4, 5)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).tupleCombinations(2))
        );
    }

    @Test
    public void basic4_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(1, 2, 3, 5),
                Arrays.asList(1, 2, 4, 5),
                Arrays.asList(1, 3, 4, 5),
                Arrays.asList(2, 3, 4, 5)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).tupleCombinations(4))
        );
    }
    @Test
    public void powerSet_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Collections.emptyList(),
                Collections.singletonList(1),
                Collections.singletonList(2),
                Collections.singletonList(3),
                Collections.singletonList(4),
                Collections.singletonList(5),
                Arrays.asList(1, 2),
                Arrays.asList(1, 3),
                Arrays.asList(1, 4),
                Arrays.asList(1, 5),
                Arrays.asList(2, 3),
                Arrays.asList(2, 4),
                Arrays.asList(2, 5),
                Arrays.asList(3, 4),
                Arrays.asList(3, 5),
                Arrays.asList(4, 5),
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 4),
                Arrays.asList(1, 2, 5),
                Arrays.asList(1, 3, 4),
                Arrays.asList(1, 3, 5),
                Arrays.asList(1, 4, 5),
                Arrays.asList(2, 3, 4),
                Arrays.asList(2, 3, 5),
                Arrays.asList(2, 4, 5),
                Arrays.asList(3, 4, 5),
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(1, 2, 3, 5),
                Arrays.asList(1, 2, 4, 5),
                Arrays.asList(1, 3, 4, 5),
                Arrays.asList(2, 3, 4, 5),
                Arrays.asList(1, 2, 3, 4, 5)
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).powerSet())
        );
    }

    @Test
    public void rPowerSet_5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
            Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5),
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(1, 2, 3, 5),
                Arrays.asList(1, 2, 4, 5),
                Arrays.asList(1, 3, 4, 5),
                Arrays.asList(2, 3, 4, 5),
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 4),
                Arrays.asList(1, 2, 5),
                Arrays.asList(1, 3, 4),
                Arrays.asList(1, 3, 5),
                Arrays.asList(1, 4, 5),
                Arrays.asList(2, 3, 4),
                Arrays.asList(2, 3, 5),
                Arrays.asList(2, 4, 5),
                Arrays.asList(3, 4, 5),
                Arrays.asList(1, 2),
                Arrays.asList(1, 3),
                Arrays.asList(1, 4),
                Arrays.asList(1, 5),
                Arrays.asList(2, 3),
                Arrays.asList(2, 4),
                Arrays.asList(2, 5),
                Arrays.asList(3, 4),
                Arrays.asList(3, 5),
                Arrays.asList(4, 5),
                Collections.singletonList(1),
                Collections.singletonList(2),
                Collections.singletonList(3),
                Collections.singletonList(4),
                Collections.singletonList(5),
                Collections.emptyList()
            ),
            CollectionsExt.toList(new Combinations<Integer>(numbers).reversePowerSet())
        );
    }

    @Test
    public void powerSet_5l3() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
                Arrays.asList(
                        Collections.emptyList(),
                        Collections.singletonList(1),
                        Collections.singletonList(2),
                        Collections.singletonList(3),
                        Collections.singletonList(4),
                        Collections.singletonList(5),
                        Arrays.asList(1, 2),
                        Arrays.asList(1, 3),
                        Arrays.asList(1, 4),
                        Arrays.asList(1, 5),
                        Arrays.asList(2, 3),
                        Arrays.asList(2, 4),
                        Arrays.asList(2, 5),
                        Arrays.asList(3, 4),
                        Arrays.asList(3, 5),
                        Arrays.asList(4, 5),
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(1, 2, 4),
                        Arrays.asList(1, 2, 5),
                        Arrays.asList(1, 3, 4),
                        Arrays.asList(1, 3, 5),
                        Arrays.asList(1, 4, 5),
                        Arrays.asList(2, 3, 4),
                        Arrays.asList(2, 3, 5),
                        Arrays.asList(2, 4, 5),
                        Arrays.asList(3, 4, 5)
                ),
                CollectionsExt.toList(new Combinations<Integer>(numbers).powerSet(3))
        );
    }

    @Test
    public void rPowerSet_5l2() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertEquals(
                Arrays.asList(
                        Arrays.asList(1, 2),
                        Arrays.asList(1, 3),
                        Arrays.asList(1, 4),
                        Arrays.asList(1, 5),
                        Arrays.asList(2, 3),
                        Arrays.asList(2, 4),
                        Arrays.asList(2, 5),
                        Arrays.asList(3, 4),
                        Arrays.asList(3, 5),
                        Arrays.asList(4, 5),
                        Collections.singletonList(1),
                        Collections.singletonList(2),
                        Collections.singletonList(3),
                        Collections.singletonList(4),
                        Collections.singletonList(5),
                        Collections.emptyList()
                ),
                CollectionsExt.toList(new Combinations<Integer>(numbers).reversePowerSet(2))
        );
    }

}
