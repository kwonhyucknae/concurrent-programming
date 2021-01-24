package com.nhk.concurrent.cache;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.GuardedBy;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class MemoizerConcurrentSingleOp<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public MemoizerConcurrentSingleOp(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> result = cache.get(arg);

            log.info("result " + result);
            if (result == null) {

                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.compute(arg);
                    }
                };

                FutureTask<V> futureTask = new FutureTask<>(eval);

                cache.putIfAbsent(arg, futureTask);
            }
            try {
                return result.get();
            } catch (CancellationException e) {
                cache.remove(arg, result);
            } catch (Exception e) {
                throw new InterruptedException("Interrupted Exception");
            }
        }
    }
}
