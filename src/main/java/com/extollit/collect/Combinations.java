package com.extollit.collect;

import com.extollit.tuple.SymmetricPair;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * MadMartian Mod
 *
 * Created by jonathan on 15-11-19.
 *
 * Copyright (c) 2015 extollIT Enterprises
 *
 * THIS IS PROPRIETARY SOFTWARE, DO NOT DISTRIBUTE
 */
public class Combinations<T> {
    private final Iterable<T> delegate;

    /**
     * Create an iterable list over the specified list that returns iterators for iterating over
     * various types of combinations of sub-sets.
     *
     * @param delegate The list to compute combinations from
     */
    public Combinations(Iterable<T> delegate) {
        this.delegate = delegate;
    }

    /**
     * Compute the number of n-tuple combinations
     *
     * @param tupleSize The size of the combination tuple, must be &gt; 0 and &lt;= delegate size
     * @return Iterator for all n-tuple combinations of the underlying list
     */
    public Iterable<List<T>> tupleCombinations(final int tupleSize) {
        return new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new CombinationsIterator<T>(Combinations.this.delegate, tupleSize);
            }
        };
    }

    /**
     * Compute the number of combination pairs
     *
     * @return Iterator for all combination pairs of the underlying list
     */
    public Iterable<SymmetricPair.Sealed<T>> pairCombinations() {
        return new Iterable<SymmetricPair.Sealed<T>>() {
            @Override
            public Iterator<SymmetricPair.Sealed<T>> iterator() {
                return new AdapterIterable.AdapterIterator<SymmetricPair.Sealed<T>, List<T>>(new CombinationsIterator<T>(Combinations.this.delegate, 2)) {
                    @Override
                    protected SymmetricPair.Sealed<T> map(List<T> input) {
                        Iterator<T> i = input.iterator();
                        return SymmetricPair.Sealed.combination(i.next(), i.next());
                    }
                };
            }
        };
    }

    public Iterable<List<T>> powerSet() {
        return new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new FlattenIterable.Iter<List<T>>(new PowerSetIter(false));
            }
        };
    }

    public Iterable<List<T>> reversePowerSet() {
        return new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new FlattenIterable.Iter<List<T>>(new PowerSetIter(true));
            }
        };
    }
    public Iterable<List<T>> powerSet(final int limit) {
        return new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new FlattenIterable.Iter<List<T>>(new PowerSetIter(false, limit));
            }
        };
    }

    public Iterable<List<T>> reversePowerSet(final int limit) {
        return new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new FlattenIterable.Iter<List<T>>(new PowerSetIter(true, limit));
            }
        };
    }

    public static <T> Iterable<List<T>> permute(final Iterable<T> combination) {
        return new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new PermutationsIterator<T>(combination);
            }
        };
    }

    private class PowerSetIter implements Iterator<Iterable<List<T>>> {
        private final boolean reverse;
        private final List<T> delegate;
        private final int limit;

        private int currentRank = 0;

        private PowerSetIter(boolean reverse) {
            this(reverse, Integer.MAX_VALUE);
        }
        private PowerSetIter(boolean reverse, int limit) {
            this.delegate = CollectionsExt.toList(Combinations.this.delegate);
            this.limit = Math.min(limit, this.delegate.size());
            if (reverse)
                this.currentRank = this.limit;
            this.reverse = reverse;
        }

        @Override
        public boolean hasNext() {
            return (!reverse && this.currentRank <= this.limit) || (reverse && this.currentRank >= 0);
        }

        @Override
        public Iterable<List<T>> next() {
            return new Iterable<List<T>>() {
                private final int rank = reverse ? currentRank-- : currentRank++;

                @Override
                public Iterator<List<T>> iterator() {
                    if (this.rank == 0)
                        return Collections.singletonList(Collections. <T> emptyList()).iterator();
                    else
                        return new CombinationsIterator<T>(PowerSetIter.this.delegate, rank);
                }
            };
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
