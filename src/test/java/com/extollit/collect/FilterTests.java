package com.extollit.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * MadMartianMod
 * <p/>
 * Created by jonathan on 31/07/16.
 */
public class FilterTests {
    @Test
    public void basic_HaT() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        Assert.assertEquals(
                Arrays.asList(2, 3),
                CollectionsExt.toList(new FilterIterable<Integer>(numbers) {
                    @Override
                    protected boolean shouldHave(Integer element) {
                        return element > 1 && element < 4;
                    }
                })
        );
    }

    @Test
    public void basic_One() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        Assert.assertEquals(
                Collections.singletonList(2),
                CollectionsExt.toList(new FilterIterable<Integer>(numbers) {
                    @Override
                    protected boolean shouldHave(Integer element) {
                        return element == 2;
                    }
                })
        );
    }


    @Test
    public void basic_empty() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        Assert.assertEquals(
                Collections.emptyList(),
                CollectionsExt.toList(new FilterIterable<Integer>(numbers) {
                    @Override
                    protected boolean shouldHave(Integer element) {
                        return element == 42;
                    }
                })
        );
    }

    @Test
    public void failureToTerminate() {
        Assert.assertEquals(
                Arrays.asList(1, 2),
                CollectionsExt.toList(
                    new Iterable<Integer>() {
                        @Override
                        public Iterator<Integer> iterator() {
                            return new FilterIterable.Iter<Integer>() {
                                private int c = 0;

                                @Override
                                protected Integer findNext() {
                                    return ++this.c % 3 == 0 ? null : this.c > 10 ? null : this.c;
                                }
                            };
                        }
                    }
                )
        );
    }
}
