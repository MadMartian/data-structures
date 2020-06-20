package com.extollit.collect;

import com.extollit.functional.Func1;
import com.extollit.tuple.Pair;

import java.util.*;

/**
 * MadMartian Mod
 * <p/>
 * Created by jonathann on 15-11-03.
 * <p/>
 * Copyright (c) 2015 extollIT Enterprises
 * <p/>
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class CollectionsExt {
    private static <E, C extends Collection<E>> C populate(C target, Iterable<E> source)
    {
        for (E item : source)
            target.add(item);
        return target;
    }
    private static <E, C extends Collection<E>> C populate(C target, Iterator<E> source)
    {
        while(source.hasNext())
            target.add(source.next());
        return target;
    }
    public static <E> List<E> toList(Iterator<E> iter) {
        return populate(new ArrayList<E>(), iter);
    }
    public static <E> List<E> toList(Iterable<E> iter) {
        return populate(new ArrayList<E>(), iter);
    }
    public static <E> Set<E> toSet(Iterator<E> iter)
    {
        return populate(new HashSet<E>(), iter);
    }
    public static <E> Set<E> toSet(Iterable<E> iter)
    {
        return populate(new HashSet<E>(), iter);
    }

    public static <K, V> Iterable<Pair.Sealed<K, V>> pairIterable(Map<K, V> source) {
        return new XFormIterable<Pair.Sealed<K, V>, Map.Entry<K, V>>(source.entrySet()) {
            @Override
            public Pair.Sealed<K, V> transform(Map.Entry<K, V> element) {
                return Pair.Sealed.of(element.getKey(), element.getValue());
            }
        };
    }

    private static <Out, In> List<Out> mapList(Iterator<In> iter, int initialCapacity, IXFormer<Out, In> xformer) {
        List<Out> result = new ArrayList<Out>(initialCapacity);

        while (iter.hasNext())
            result.add(xformer.transform(iter.next()));
        return result;
    }

    public static <Out, In> List<Out> mapList(Iterable<In> iter, IXFormer<Out, In> xformer) {
        return mapList(iter.iterator(), 0, xformer);
    }

    public static <Out, In> List<Out> mapList(List<In> list, IXFormer<Out, In> xformer) {
        return mapList(list.iterator(), list.size(), xformer);
    }

    public static <L, R> Iterable<Pair.Sealed<L, R>> zip(Iterable<L> left, Iterable<R> right) {
        return new ZipIterable<L, R>(left, right);
    }

    public static <T> Iterable<T> flatten(Iterable<? extends Iterable<T>> argument) {
        return new FlattenIterable<T>(argument);
    }
    public static <T> Iterable<T> flatten(Iterable<? extends T> first, Iterable<? extends T> second) {
        return new FlattenIterable<T>(first, second);
    }
    public static <T> Iterable<T> flatten(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<T> third) {
        return new FlattenIterable<T>(first, second, third);
    }
    public static <T> Iterable<T> flatten(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth) {
        return new FlattenIterable<T>(first, second, third, fourth);
    }
    public static <T> Iterable<T> flatten(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth, Iterable<? extends T> fifth) {
        return new FlattenIterable<T>(first, second, third, fourth, fifth);
    }
    public static <T> Iterable<T> flatten(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth, Iterable<? extends T> fifth, Iterable<? extends T> sixth) {
        return new FlattenIterable<T>(first, second, third, fourth, fifth, sixth);
    }
    public static <T> Iterable<T> flatten(Iterable<? extends T> first, Iterable<? extends T> second, Iterable<? extends T> third, Iterable<? extends T> fourth, Iterable<? extends T> fifth, Iterable<? extends T> sixth, Iterable<? extends T> seventh) {
        return new FlattenIterable<T>(first, second, third, fourth, fifth, sixth, seventh);
    }
    public static < Key, Value > void load(Iterable<? extends Pair.Sealed<Key, ? extends Value>> source, Map<Key, Value> dest) {
        for (Pair.Sealed<Key, ? extends Value> pair : source)
            dest.put(pair.left, pair.right);
    }

    public static <A, B> Map<A, List<B>> groupBy(Collection<B> collection, final Func1<A, B> selector) {
        @SuppressWarnings({"unchecked", "SuspiciousToArrayCall"})
        B[] array = (B[]) collection.toArray(new Object[collection.size()]);

        Arrays.sort(
            array,
            new Comparator<B>() {
                @Override
                public int compare(B left, B right) {
                    return selector.apply(left).hashCode() - selector.apply(right).hashCode();
                }
            }
        );

        Map<A, List<B>> map = new Hashtable<A, List<B>>();
        A keyed0 = null;
        List<B> current = null;
        for (B x : array) {
            A keyed = selector.apply(x);
            if (keyed0 == null || !keyed.equals(keyed0)) {
                current = new LinkedList<B>();
                map.put(keyed, current);
            }
            current.add(x);
            keyed0 = keyed;
        }
        return map;
    }

    public static < T > boolean anyNullIn(T[] array) {
        for (T entry : array)
            if (entry == null)
                return true;

        return false;
    }

    public static < T > boolean anyNonNullIn(T[] array) {
        for (T entry : array)
            if (entry != null)
                return true;

        return false;
    }
}
