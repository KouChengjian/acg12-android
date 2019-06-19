package com.acg12.lib.net.result;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by AItsuki on 2018/7/24.
 */
public final class Taker<T> {

    private static final Taker<?> EMPTY = new Taker<>();

    private final T value;

    private Taker() {
        this.value = null;
    }

    public static <T> Taker<T> empty() {
        @SuppressWarnings("unchecked")
        Taker<T> t = (Taker<T>) EMPTY;
        return t;
    }

    private Taker(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Taker<T> of(T value) {
        return new Taker<>(value);
    }

    public static <T> Taker<T> ofNullable(T value) {
//        return value == null ? empty() : of(value);
        return value == null ? (Taker<T>) empty() : of(value);
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }
}
