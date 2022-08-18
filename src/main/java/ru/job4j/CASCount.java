package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count;

    public CASCount(AtomicInteger count) {
        this.count = count;
    }

    public void increment() {
        int currCount;
        int newCount;
        do {
            currCount = count.get();
            newCount = currCount + 1;
        } while (!count.compareAndSet(currCount, newCount));
    }

    public int get() {
        return count.get();
    }
}
