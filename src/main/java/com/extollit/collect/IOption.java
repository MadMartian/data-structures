package com.extollit.collect;

import java.util.Collection;

public interface IOption<T> extends Collection<T> {
    T get();

    IOption<T> or(IOption<T> other);
}
