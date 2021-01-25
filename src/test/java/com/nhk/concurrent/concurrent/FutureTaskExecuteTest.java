package com.nhk.concurrent.concurrent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FutureTaskExecuteTest {
    @Test
    void executeFutureTask() {
        try {
            int numberOfThreads = 2;
            ExecutorService service = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(numberOfThreads);

            for (int i = 0; i < numberOfThreads; i++) {

                service.execute(() -> {
                    FutureTaskExecute futureTaskExecute = new FutureTaskExecute();
                    futureTaskExecute.start();
                    try {
                        System.out.println(futureTaskExecute.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    latch.countDown();
                });

                service.execute(() -> {
                    FutureTaskExecute futureTaskExecute = new FutureTaskExecute();
                    futureTaskExecute.start();
                    try {
                        System.out.println(futureTaskExecute.get());
                    } catch (Exception e) {
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