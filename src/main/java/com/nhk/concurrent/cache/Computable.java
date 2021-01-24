package com.nhk.concurrent.cache;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
