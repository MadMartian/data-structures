/*
 * MadMartian Mod - Copyright (c) 2016 extollIT Enterprises.
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE!
 */

package com.extollit.test.property;

import com.extollit.num.IntRange;

import java.util.*;

/**
 * main
 * <p/>
 * Created by jonathan on 25/12/16.
 */
public class CollectionGenerators {
    public static final class SetGen<T> extends AbstractCollectionGenerator<T, Set<T>> implements IGenerator<Set<T>> {
        public SetGen(IGenerator<T> elementGenerator, IntRange quantityRange) {
            super(elementGenerator, quantityRange);
        }

        protected Set<T> createCollection(int capacity) {
            return new HashSet<T>(capacity);
        }
    }

    public static final class EnumSetGen<E extends Enum<E>> extends AbstractCollectionGenerator<E, EnumSet<E>> implements IGenerator<EnumSet<E>> {
        public final Class<E> clazz;

        public EnumSetGen(Class<E> clazz, IntRange quantityRange) {
            super(new EnumGen<E>(clazz), quantityRange);
            this.clazz = clazz;
        }

        public EnumSetGen(Class<E> clazz) {
            this(clazz, new IntRange(0, clazz.getEnumConstants().length));
        }

        @Override
        protected EnumSet<E> createCollection(int capacity) {
            return EnumSet.noneOf(clazz);
        }
    }

    public static final class ListGen<T> extends AbstractCollectionGenerator<T, List<T>> implements IGenerator<List<T>> {
        public ListGen(IGenerator<T> elementGenerator, IntRange quantityRange) {
            super(elementGenerator, quantityRange);
        }

        protected List<T> createCollection(int capacity) {
            return new ArrayList<T>(capacity);
        }
    }

}
