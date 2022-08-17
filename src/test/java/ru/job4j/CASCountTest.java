package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenCountingTo25() throws InterruptedException {
        CASCount counter = new CASCount(new AtomicReference<>(0));
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        counter.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        counter.increment();
                    }
                }
        );
        Thread third = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
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
        assertThat(counter.get()).isEqualTo(25);
    }
}
