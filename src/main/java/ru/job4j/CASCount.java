package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount(final AtomicReference<Integer> count) {
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
