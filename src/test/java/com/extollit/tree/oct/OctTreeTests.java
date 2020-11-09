/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.oct;

import com.extollit.collect.CollectionsExt;
import com.extollit.collect.XFormIterable;
import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.immutable.Vec3i;
import com.extollit.num.IntRange;
import com.extollit.test.property.CollectionGenerators;
import com.extollit.test.property.SelectItemGen;
import com.extollit.test.property.immutable.Generators;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * main
 * <p/>
 * Created by jonathan on 05/12/16.
 */
public class OctTreeTests {
    private static final IntAxisAlignedBox
        ALPHA = new IntAxisAlignedBox(100, 100, 100, 200, 200, 200),
        BETA = new IntAxisAlignedBox(-40, -40, -40, -20, -20, -20),
        GAMMA = new IntAxisAlignedBox(+40, -40, +40, +80, 0, +80),
        DELTA = new IntAxisAlignedBox(+40, -40, -40, +80, 0, -20),
        ETA = new IntAxisAlignedBox(-5, 0, 5, 5, 10, 15),
        THETA = new IntAxisAlignedBox(-10, 2, 0, 10, 20, 5),
        IOTA = new IntAxisAlignedBox(-20, -20, -20, 40, 40, 40),
        KAPPA = new IntAxisAlignedBox(-20, 0, 0, 0, 20, 20);

    private Set<String> actualize(final Iterator<OctTree.Entry<String>> iterator) {
        return CollectionsExt.toSet(
                new XFormIterable.Iter<String, OctTree.Entry<String>> (iterator) {
                    @Override
                    public String transform(OctTree.Entry<String> element) {
                        return element.value;
                    }
                }
        );
    }

    @Test
    public void find1in2() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "Find Me!");
        tree.add(BETA, "Don't Find Me!");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new Vec3i(120, 101, 199));

        assertTrue(iterator.hasNext());
        assertEquals("Find Me!", iterator.next().value);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void find0in2() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "Garbage");
        tree.add(BETA, "Don't Find Me!");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new Vec3i(50, 101, 199));
        assertFalse("Expected iterator to be empty", iterator.hasNext());
    }

    @Test
    public void find1in4() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "Garbage in");
        tree.add(BETA, "Don't Find Me!");
        tree.add(GAMMA, "Garbage out");
        tree.add(DELTA, "Find Me!");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new Vec3i(70, -1, -30));

        assertTrue(iterator.hasNext());
        assertEquals("Find Me!", iterator.next().value);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void find0in4() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "Garbage in");
        tree.add(BETA, "Don't Find Me!");
        tree.add(GAMMA, "Garbage out");
        tree.add(DELTA, "Find Me!");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new Vec3i(70, 1, -30));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void find4in4() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ETA, "eta");
        tree.add(THETA, "theta");
        tree.add(IOTA, "iota");
        tree.add(KAPPA, "kappa");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new Vec3i(0, 2, 5));
        Set<String> actual = actualize(iterator);
        assertEquals(new HashSet<String>(Arrays.asList("eta", "theta", "iota", "kappa")), actual);
    }

    @Test
    public void find4in8() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "alpha");
        tree.add(BETA, "beta");
        tree.add(GAMMA, "gamma");
        tree.add(DELTA, "delta");
        tree.add(ETA, "eta");
        tree.add(THETA, "theta");
        tree.add(IOTA, "iota");
        tree.add(KAPPA, "kappa");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new Vec3i(0, 2, 5));
        Set<String> actual = actualize(iterator);
        assertEquals(new HashSet<String>(Arrays.asList("eta", "theta", "iota", "kappa")), actual);
    }

    @Test
    public void findAll8() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "alpha");
        tree.add(BETA, "beta");
        tree.add(GAMMA, "gamma");
        tree.add(DELTA, "delta");
        tree.add(ETA, "eta");
        tree.add(THETA, "theta");
        tree.add(IOTA, "iota");
        tree.add(KAPPA, "kappa");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll();
        Set<String> actual = actualize(iterator);
        assertEquals(new HashSet<String>(Arrays.asList("eta", "theta", "iota", "kappa", "alpha", "beta", "gamma", "delta")), actual);
    }

    @Test
    public void find2inBox() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "alpha");
        tree.add(BETA, "beta");
        tree.add(GAMMA, "gamma");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new IntAxisAlignedBox(70, 0, 65, 120, 150, 135));
        Set<String> actual = actualize(iterator);
        assertEquals(new HashSet<String>(Arrays.asList("alpha", "gamma")), actual);
    }

    @Test
    public void find4inBox() {
        OctTree<String> tree = new OctTree<String>();

        tree.add(ALPHA, "alpha");
        tree.add(BETA, "beta");
        tree.add(GAMMA, "gamma");
        tree.add(DELTA, "delta");
        tree.add(ETA, "eta");
        tree.add(THETA, "theta");
        tree.add(IOTA, "iota");
        tree.add(KAPPA, "kappa");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new IntAxisAlignedBox(-5, -5, -5, 40, 40, 40));
        Set<String> actual = actualize(iterator);
        assertEquals(new HashSet<String>(Arrays.asList("eta", "theta", "iota", "kappa", "gamma")), actual);
    }

    @Test
    public void empty() {
        OctTree<String> tree = new OctTree<String>();
        assertFalse(tree.findAll().hasNext());
        assertFalse(tree.findAll(new IntAxisAlignedBox(-5, -5, -5, +5, +5, +5)).hasNext());
    }

    @Test
    public void arbitrary1() {
        final IntAxisAlignedBox
            alpha = new IntAxisAlignedBox(-103, -91, -86, 97, 109, 114),
            beta = new IntAxisAlignedBox(-13, -1, 4, 7, 19, 24),
            gamma = new IntAxisAlignedBox(-34, 2, -8, -24, 12, 2);

        OctTree<String> tree = new OctTree<String>();
        tree.add(alpha, "alpha");
        tree.add(beta, "beta");
        tree.add(gamma, "gamma");
        Iterator<OctTree.Entry<String>> iterator = tree.findAll(new IntAxisAlignedBox(-287, Integer.MIN_VALUE >> 1, -260, 225, Integer.MAX_VALUE >> 1, 252));
        assertEquals(
            new HashSet<String>(Arrays.asList("alpha", "beta", "gamma")),
            actualize(iterator)
        );
    }

    @Test
    public void removeAll() {
        final IntAxisAlignedBox
                alpha = new IntAxisAlignedBox(-103, -91, -86, 97, 109, 114),
                beta = new IntAxisAlignedBox(-13, -1, 4, 7, 19, 24),
                gamma = new IntAxisAlignedBox(-34, 2, -8, -24, 12, 2);

        OctTree<String> tree = new OctTree<String>();
        tree.add(alpha, "alpha");
        tree.add(beta, "beta");
        tree.add(gamma, "gamma");

        Iterator<OctTree.Entry<String>> iterator = tree.findAll();
        int counter = 0;
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            counter++;
        }

        assertEquals(3, counter);
        assertFalse(tree.findAll().hasNext());
    }

    @Test
    public void removeSeparateBuckets() {
        final IntAxisAlignedBox
                alpha = new IntAxisAlignedBox(-103, -91, -86, 97, 109, 114),
                beta = new IntAxisAlignedBox(-13, -1, 4, 7, 19, 24),
                gamma = new IntAxisAlignedBox(-34, 2, -8, -24, 12, 2);

        OctTree<String> tree = new OctTree<String>();
        tree.add(alpha, "alpha");
        tree.add(beta, "beta");
        tree.add(gamma, "gamma");

        Iterator<OctTree.Entry<String>> iterator = tree.findAll();
        int counter = 0;
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            counter++;
        }

        assertEquals(3, counter);
        assertFalse(tree.findAll().hasNext());
    }

    @Test
    public void redundant() {
        final IntAxisAlignedBox
                alpha = new IntAxisAlignedBox(-103, -91, -86, 97, 109, 114),
                alpha2 = new IntAxisAlignedBox(-103, -91, -86, 97, 109, 114),
                beta = new IntAxisAlignedBox(-13, -1, 4, 7, 19, 24);

        OctTree<String> tree = new OctTree<String>();
        tree.add(alpha, "alpha");
        tree.add(alpha2, "alpha");
        tree.add(beta, "beta");

        Iterator<OctTree.Entry<String>> iterator = tree.findAll();
        int counter = 0;
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            counter++;
        }

        assertEquals(2, counter);
        assertFalse(tree.findAll().hasNext());
    }

    public static final class IntegrityPropertyTest {
        private final SelectItemGen<String> itemsGen;
        private final Generators.IntAxisAlignedBoxGen boxGen;
        private final OctTreeEntryGen<String> entryGen;
        private final IntRange quantityRange;
        private final CollectionGenerators.ListGen<OctTree.Entry<String>> entryListGen;

        private Random random = new Random(0);

        public IntegrityPropertyTest() {
            this.itemsGen = new SelectItemGen<String>("alpha", "beta", "gamma", "delta", "eta", "theta", "iota", "kappa");
            this.boxGen = new Generators.IntAxisAlignedBoxGen(Vec3i.ZERO, 5000);
            this.entryGen = new OctTreeEntryGen<String>(boxGen, itemsGen);
            this.quantityRange = new IntRange(1, 40);
            this.entryListGen = new CollectionGenerators.ListGen<OctTree.Entry<String>>(entryGen, quantityRange);
        }

        public void test() {
            int iterations = 100;
            while (iterations-- > 0) {
                long seed = nextSeed();
                System.err.println("Seed " + seed);

                final OctTree<String> tree = setupTree(random);
                int subiter = 1000;
                while (subiter-- > 0) {
                    nextSeed();
                    test(tree);
                }
            }
        }

        protected long nextSeed() {
            long seed = random.nextLong();
            random = new Random(seed);
            return seed;
        }

        public void test(OctTree<String> tree) {
            final IntAxisAlignedBox query = boxGen.next(random);
            Set<OctTree.Entry<String>> found = new HashSet<OctTree.Entry<String>>();
            Iterator<OctTree.Entry<String>> i = tree.findAll(query);
            while (i.hasNext())
                found.add(i.next());

            i = tree.findAll();
            while (i.hasNext()) {
                OctTree.Entry<String> entry = i.next();
                if (found.contains(entry))
                    assertTrue(Assert.format("Boxes must intersect", entry.key, query), entry.key.intersects(query));
                else
                    assertFalse(Assert.format("Boxes should not intersect", entry.key, query), entry.key.intersects(query));
            }
        }

        protected OctTree<String> setupTree(Random random) {
            final OctTree<String> tree = new OctTree<String>();
            final List<OctTree.Entry<String>> entries = entryListGen.next(random);

            for (OctTree.Entry<String> entry : entries)
                tree.add(entry.key, entry.value);
            return tree;
        }
    }

    @Test
    public void integrity() {
        new IntegrityPropertyTest().test();
    }
}
