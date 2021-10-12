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
public class PermutationsIterator<T> extends FilterIterable.Iter<List<T>> {
    private static final class StackEntry {
        public int index;
        public final int [] memory;

        private StackEntry(int rank) {
            this.memory = new int[rank];
        }
    }

    private final List<T> delegate, workspace;
    private final StackEntry[] indexStack;

    private int stackPointer;

    public PermutationsIterator(Iterable<T> delegate) {
        this.delegate = delegate instanceof List ? (List<T>) delegate : CollectionsExt.toList(delegate);
        final boolean empty = this.delegate.isEmpty();
        if (empty) {
            this.stackPointer = -1;
            this.indexStack = null;
            this.workspace = null;
        } else {
            this.stackPointer = 0;
            this.indexStack = new StackEntry[this.delegate.size()];
            this.workspace = new ArrayList<T>(this.delegate);
            for (int c = 0; c < this.indexStack.length; ++c) {
                final StackEntry stackEntry = this.indexStack[c] = new StackEntry(this.indexStack.length - c);
                stackEntry.index = c;

                for (int j = 0; j < stackEntry.memory.length; ++j)
                    stackEntry.memory[j] = c + j;
            }
        }
    }

    @Override
    protected List<T> findNext() {
        if (stackPointer >= 0) {
            final StackEntry current = indexStack[stackPointer];

            {
                final int
                        mem0 = current.memory[0],
                        j = current.index - stackPointer;
                current.memory[0] = current.memory[j];
                current.memory[j] = mem0;
            }

            for (int j = stackPointer + 1; j < indexStack.length; ++j) {
                final StackEntry thisStackEntry = indexStack[j];
                System.arraycopy(current.memory, j - stackPointer, thisStackEntry.memory, 0, thisStackEntry.memory.length);
            }

            for (int c = stackPointer; c < indexStack.length; ++c)
                workspace.set(c, delegate.get(current.memory[c - stackPointer]));

            stackPointer = indexStack.length - 1;
            while (stackPointer >= 0 && ++indexStack[stackPointer].index >= indexStack.length)
                indexStack[stackPointer].index = stackPointer--;

            return new ArrayList<T>(this.workspace);
        } else
            return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
            "Permutations / combinations iterator does not support eviction due it's non-injective delegate collection functional mapping... such compleat, very grandiloquence, wow"
        );
    }
}
