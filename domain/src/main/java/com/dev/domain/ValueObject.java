package com.dev.domain;

public interface ValueObject<T> {
    void validateValue(final T value);
    T value();
}
