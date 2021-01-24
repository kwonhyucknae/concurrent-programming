package com.nhk.concurrent.cache;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Memoizer<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> computable;

    public Memoizer(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);

        log.info("result " + result);
        if (result == null) {
            result = computable.compute(arg);

            cache.put(arg, result);
        }
        return result;
    }
}
