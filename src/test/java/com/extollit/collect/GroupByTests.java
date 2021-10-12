package com.extollit.collect;

import com.extollit.functional.Func1;
import com.extollit.tuple.Pair;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * MadMartianMod
 *
 * Created by jonathan on 31/07/16.
 */
public class GroupByTests {
    private class SomeObject {
        public final String key;
        public final int value;

        private SomeObject(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + this.key + ", " + this.value + ")";
        }
    }

    @SafeVarargs
    private final <K, V> Map<K, V> map(Pair.Sealed<K, V>... entries) {
        Map<K, V> result = new HashMap<K, V>();
        for (Pair.Sealed<K, V> pair : entries)
            result.put(pair.left, pair.right);
        return result;
    }

    @Test
    public void basic() {
        ArrayList<SomeObject> input = new ArrayList<SomeObject>(
            Arrays.asList(
                new SomeObject("foo", 42),
                new SomeObject("bar", 21),
                new SomeObject("bar", 39),
                new SomeObject("gong", 89),
                new SomeObject("bar", 20)
            )
        );

        Assert.assertEquals(
            map(
                Pair.Sealed.of("bar", Arrays.asList(input.get(1), input.get(2), input.get(4))),
                Pair.Sealed.of("foo", Collections.singletonList(input.get(0))),
                Pair.Sealed.of("gong", Collections.singletonList(input.get(3)))
            ),
            CollectionsExt.groupBy(
                input,
                new Func1<String, SomeObject>() {
                    @Override
                    public String apply(SomeObject x) {
                        return x.key;
                    }
                }
            )
        );
    }


    @Test
    public void unique() {
        ArrayList<SomeObject> input = new ArrayList<SomeObject>(
                Arrays.asList(
                        new SomeObject("foo", 42),
                        new SomeObject("bar", 39),
                        new SomeObject("gong", 89)
                )
        );

        Assert.assertEquals(
                map(
                        Pair.Sealed.of("bar", Collections.singletonList(input.get(1))),
                        Pair.Sealed.of("foo", Collections.singletonList(input.get(0))),
                        Pair.Sealed.of("gong", Collections.singletonList(input.get(2)))
                ),
                CollectionsExt.groupBy(
                        input,
                        new Func1<String, SomeObject>() {
                            @Override
                            public String apply(SomeObject x) {
                                return x.key;
                            }
                        }
                )
        );
    }
}
