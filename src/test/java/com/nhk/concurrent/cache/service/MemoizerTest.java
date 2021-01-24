package com.nhk.concurrent.cache.service;

import com.nhk.concurrent.cache.Computable;
import com.nhk.concurrent.cache.Memoizer;
import com.nhk.concurrent.cache.MemoizerConcurrent;
import com.nhk.concurrent.cache.MemoizerConcurrentSingleOp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
class MemoizerTest {

    private final Computable<Integer, Integer> computable =
            new Computable<Integer, Integer>() {
                @Override
                public Integer compute(Integer arg) throws InterruptedException {
                    return arg + 1;
                }
            };

    @Test
    void computeMemoizer() {
        Memoizer memoizer = new Memoizer(computable);
        try {
            int numberOfThreads = 5;
            ExecutorService service = Executors.newFixedThreadPool(5);
            CountDownLatch latch = new CountDownLatch(numberOfThreads);

            for (int i = 0; i < numberOfThreads; i++) {

                service.execute(() -> {
                    try {
                        memoizer.compute(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                });
            }
            latch.await();
//            assertEquals(numberOfThreads, memoizer.compute("test"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void computeMemoizerConcurrent() {
        MemoizerConcurrent memoizer = new MemoizerConcurrent(computable);
        try {
            int numberOfThreads = 2;
            ExecutorService service = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(numberOfThreads);

            for (int i = 0; i < numberOfThreads; i++) {

                service.execute(() -> {
                    try {
                        memoizer.compute(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                });

                service.execute(() -> {
                    try {
                        memoizer.compute(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                });
            }
            latch.await();
//            assertEquals(numberOfThreads, memoizer.compute("test"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void computeMemoizerConcurrentSingleOp() {
        MemoizerConcurrentSingleOp memoizer = new MemoizerConcurrentSingleOp(computable);
        try {
            int numberOfThreads = 2;
            ExecutorService service = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(numberOfThreads);

            for (int i = 0; i < numberOfThreads; i++) {

                service.execute(() -> {
                    try {
                        memoizer.compute(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                });

                service.execute(() -> {
                    try {
                        memoizer.compute(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                });
            }
            latch.await();
//            assertEquals(numberOfThreads, memoizer.compute("test"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

