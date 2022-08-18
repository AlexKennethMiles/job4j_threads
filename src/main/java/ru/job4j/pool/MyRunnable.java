package ru.job4j.pool;

import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable {
    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        System.out.println("Counter value is: " + count.incrementAndGet());
    }
}
