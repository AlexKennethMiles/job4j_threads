package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenEmptyCounter() {
        CASCount count = new CASCount();
        assertThat(count.get()).isEqualTo(0);
    }

    @Test
    public void whenIncrementTenTimes() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(10);
    }

    @Test
    public void whenThreeThreadsIncrementToThirty() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                count.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                count.increment();
            }
        });
        Thread third = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                count.increment();
            }
        });
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(count.get()).isEqualTo(30);
    }
}
