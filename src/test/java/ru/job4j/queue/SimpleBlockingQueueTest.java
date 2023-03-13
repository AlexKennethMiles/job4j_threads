package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);

    @Test
    public void whenOneOfferedOnePolled() throws InterruptedException {
        AtomicInteger polled = new AtomicInteger();
        Thread consumer = new Thread(() -> {
            polled.set(queue.poll());
        }, "Consumer");
        Thread producer = new Thread(() -> {
            queue.offer(1);
        }, "Producer");
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(polled.get()).isEqualTo(1);
    }

    @Test
    public void whenFiveOfferedFivePolled() throws InterruptedException {
        AtomicInteger polled = new AtomicInteger();
        List<Integer> list = new ArrayList<>();
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Integer value = queue.poll();
                list.add(value);
                polled.set(value);
            }
        }, "Consumer");
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.offer(i);
            }
        }, "Producer");
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(polled.get()).isEqualTo(4);
        assertThat(list).isEqualTo(List.of(0, 1, 2, 3, 4));
    }

    @Test
    public void whenFiveOfferedOnePolled() throws InterruptedException {
        AtomicInteger polled = new AtomicInteger(-1);
        Thread consumer = new Thread(() -> {
            polled.set(queue.poll());
        }, "Consumer");
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.offer(i);
            }
        }, "Producer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(polled.get()).isEqualTo(0);
    }

    @Test
    public void whenProducerWaitConsumer() throws InterruptedException {
        AtomicInteger polled = new AtomicInteger(-1);
        List<Integer> list = new ArrayList<>();
        Thread initial = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.offer(i);
            }
        }, "Producer");
        initial.start();
        initial.join();
        Thread producer = new Thread(() -> {
            for (int i = 5; i < 10; i++) {
                queue.offer(i);
            }
        }, "Producer");
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Integer value = queue.poll();
                list.add(value);
                polled.set(value);
            }
        }, "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).isEqualTo(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }
}
