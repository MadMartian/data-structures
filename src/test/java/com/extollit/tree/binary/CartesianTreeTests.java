/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.tree.binary;

import com.extollit.collect.CollectionsExt;
import com.extollit.collect.XFormIterable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CartesianTreeTests {
    private CartesianTree<Integer> tree;

    @Before
    public void setup () {
        this.tree = new CartesianTree<Integer>(
            new Comparator<Integer>() {
                @Override
                public int compare(Integer left, Integer right) {
                    return left - right;
                }
            }
        );
    }

    @After
    public void teardown() {
        this.tree = null;
    }

    @Test
    public void basic() {
        List<Integer> sample = Arrays.asList(1, 5, 3, 18, 15, 12, 6, 2, 4, 9);

        for (Integer i : sample)
            this.tree.insert(i);

        assertEquals(
            sample,
            CollectionsExt.toList(
                new XFormIterable.Iter<Integer, ITreeNode<Integer, ?>>(this.tree.inOrderIterator()) {
                    @Override
                    public Integer transform(ITreeNode<Integer, ?> element) {
                        return element.data();
                    }
                }
            )
        );
    }

    @Test
    public void postOrder() {
        List<Integer>
            sample = Arrays.asList(9, 3, 7, 1, 8, 12, 10, 20, 15, 18, 5),
            expectation = Arrays.asList(9, 7, 3, 12, 20, 18, 15, 10, 8, 5, 1);

        for (Integer i : sample)
            this.tree.insert(i);

        assertEquals(
            expectation,
            CollectionsExt.toList(
                new XFormIterable.Iter<Integer, ITreeNode<Integer, ?>>(this.tree.postOrderIterator()) {
                    @Override
                    public Integer transform(ITreeNode<Integer, ?> element) {
                        return element.data();
                    }
                }
            )
        );
    }

    @Test
    public void preOrder() {
        List<Integer>
                sample = Arrays.asList(9, 3, 7, 1, 8, 12, 10, 20, 15, 18, 5),
                expectation = Arrays.asList(1, 3, 9, 7, 5, 8, 10, 12, 15, 20, 18);

        for (Integer i : sample)
            this.tree.insert(i);

        assertEquals(
                expectation,
                CollectionsExt.toList(
                        new XFormIterable<Integer, ITreeNode<Integer, ?>>(
                                new Iterable<Node<Integer>>() {
                                    @Override
                                    public Iterator<Node<Integer>> iterator() {
                                        return CartesianTreeTests.this.tree.preOrderIterator();
                                    }
                                }
                        ) {
                            @Override
                            public Integer transform(ITreeNode<Integer, ?> element) {
                                return element.data();
                            }
                        }
                )
        );
    }

    @Test
    public void levelOrder() {
        List<Integer>
                sample = Arrays.asList(9, 3, 7, 1, 8, 12, 10, 20, 15, 18, 5),
                expectation = Arrays.asList(1, 3, 5, 9, 7, 8, 10, 12, 15, 20, 18);

        for (Integer i : sample)
            this.tree.insert(i);

        assertEquals(
                expectation,
                CollectionsExt.toList(
                        new XFormIterable<Integer, ITreeNode<Integer, ?>>(
                                new Iterable<Node<Integer>>() {
                                    @Override
                                    public Iterator<Node<Integer>> iterator() {
                                        return CartesianTreeTests.this.tree.levelOrderIterator();
                                    }
                                }
                        ) {
                            @Override
                            public Integer transform(ITreeNode<Integer, ?> element) {
                                return element.data();
                            }
                        }
                )
        );
    }

    @Test
    public void permutations() {
        Random random = new Random(0);

        for (int c = 0; c < 1000; ++c) {
            setup();
            List<Integer> sample = randomList(random);

            for (int data : sample)
                this.tree.insert(data);

            assertEquals(
                sample,
                CollectionsExt.toList(
                    new XFormIterable.Iter<Integer, ITreeNode<Integer, ?>>(this.tree.inOrderIterator()) {
                        @Override
                        public Integer transform(ITreeNode<Integer, ?> element) {
                            return element.data();
                        }
                    }
                )
            );
            teardown();
        }
    }

    private List<Integer> randomList(Random random) {
        final int size = random.nextInt(20);
        List<Integer> sample = new ArrayList<Integer>(size);
        for (int i = 0; i < size; ++i) {
            int data = random.nextInt(100);
            sample.add(data);
        }
        return sample;
    }
}
