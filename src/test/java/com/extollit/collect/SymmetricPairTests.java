package com.extollit.collect;

import com.extollit.tuple.SymmetricPair;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * MadMartianMod
 *
 * Created by jonathan on 31/07/16.
 */
public class SymmetricPairTests {
    @Test
    public void set_equivalence() {
        Set<Integer> formal = new HashSet<Integer>(Arrays.asList(1, 3));
        Set<Integer> pair = SymmetricPair.combination(3, 1).toSet();

        Assert.assertTrue(pair.equals(formal));
        Assert.assertTrue(formal.equals(pair));
    }

    @Test
    public void set_nonEquivalence() {
        Set<Integer> formal = new HashSet<Integer>(Arrays.asList(1, 2));
        Set<Integer> pair = SymmetricPair.combination(3, 1).toSet();

        Assert.assertFalse(pair.equals(formal));
        Assert.assertFalse(formal.equals(pair));
    }

    @Test
    public void set_disparateSizeNonEquivalence() {
        Set<Integer> formal = new HashSet<Integer>(Arrays.asList(1, 3, 5));
        Set<Integer> pair = SymmetricPair.combination(3, 1).toSet();

        Assert.assertFalse(pair.equals(formal));
        Assert.assertFalse(formal.equals(pair));
    }

    @Test
    public void set_nestedMembership() {
        Set<Integer> formal = new HashSet<Integer>(Arrays.asList(4, 5));
        Set<Integer> pair = SymmetricPair.combination(4, 5).toSet();
        Set<Set<Integer>>
                nesting = Collections.singleton(formal),
                nestingCommuted = Collections.singleton(pair);

        Assert.assertTrue(nesting.contains(pair));
        Assert.assertTrue(nestingCommuted.contains(formal));
    }

    @Test
    public void set_nestedNonMembership() {
        Set<Integer> formal = new HashSet<Integer>(Arrays.asList(1, 3));
        Set<Integer> pair = SymmetricPair.combination(4, 6).toSet();
        Set<Set<Integer>>
                nesting = Collections.singleton(formal),
                nestingCommuted = Collections.singleton(pair);

        Assert.assertFalse(nesting.contains(pair));
        Assert.assertFalse(nestingCommuted.contains(formal));
    }

    @Test
    public void set_disparateSizedNestedNonMembership() {
        Set<Integer> formal = new HashSet<Integer>(Arrays.asList(1, 3, 7));
        Set<Integer> pair = SymmetricPair.combination(4, 6).toSet();
        Set<Set<Integer>>
                nesting = Collections.singleton(formal),
                nestingCommuted = Collections.singleton(pair);

        Assert.assertFalse(nesting.contains(pair));
        Assert.assertFalse(nestingCommuted.contains(formal));
    }
}
