package com.nhk.concurrent.cache;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.GuardedBy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MemoizerConcurrent<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public MemoizerConcurrent(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);

        log.info("result " + result);
        if (result == null) {
            result = computable.compute(arg);

            cache.put(arg, result);
        }
        return result;
    }
}
