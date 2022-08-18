package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenCountingTo250000() throws InterruptedException {
        CASCount counter = new CASCount(new AtomicInteger(0));
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100_000; i++) {
                        counter.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 75_000; i++) {
                        counter.increment();
                    }
                }
        );
        Thread third = new Thread(
                () -> {
                    for (int i = 0; i < 75_000; i++) {
                        counter.increment();
                    }
                }
        );
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(counter.get()).isEqualTo(250_000);
    }
}
