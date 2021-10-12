package com.extollit.collect;

import java.util.ArrayList;
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
public class CombinationsIterator<T> extends FilterIterable.Iter<List<T>> {
    private final List<T> delegate, workspace;
    private final int[] indexStack;
    private final int spanSize;

    private int stackPointer;

    public CombinationsIterator(Iterable<T> delegate, int rank) {
        this.delegate = CollectionsExt.toList(delegate);
        this.indexStack = new int[rank];
        this.workspace = new ArrayList<T>(rank);
        this.stackPointer = 0;
        this.indexStack[0] = -1;
        this.spanSize = this.delegate.size() - this.indexStack.length;
        for (int c = 0; c < this.indexStack.length; ++c)
            this.workspace.add(c, null);
    }

    @Override
    protected List<T> findNext() {
        while (stackPointer >= 0 && ++indexStack[stackPointer] > spanSize + stackPointer)
            stackPointer--;

        if (stackPointer >= 0) {
            workspace.set(stackPointer, delegate.get(indexStack[stackPointer]));
            for (int c = stackPointer + 1; c < indexStack.length; ++c) {
                indexStack[c] = indexStack[c - 1] + 1;
                ++stackPointer;
                workspace.set(c, delegate.get(indexStack[c]));
            }
            return new ArrayList<T>(workspace);
        } else
            return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
            "Combinations iterator does not support removal of combinations because it's functional mapping to the underlying collection is non-injective"
        );
    }
}
