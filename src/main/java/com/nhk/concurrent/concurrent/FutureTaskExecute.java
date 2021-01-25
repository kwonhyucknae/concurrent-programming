package com.nhk.concurrent.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskExecute {
    static Integer CONSTANT_INTEGER = 0;

    private final FutureTask<Integer> future = new FutureTask<>(new Callable<Integer>(){

        @Override
        public Integer call() throws Exception {
            return CONSTANT_INTEGER++;
        }
    });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public Integer get() throws Exception {
        while (true) {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("");
            }
        }
    }
}
