package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    @GuardedBy("monitor")
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(3);
        for (int i = 1; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is sleeping...");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " get started!");
            }, "Awaiter-" + i).start();
        }
        for (int i = 1; i < 4; i++) {
            new Thread(() -> {
                barrier.count();
                System.out.println(Thread.currentThread().getName() + " is increasing counter!");
            }, "Counter-" + i).start();
        }
    }
}
