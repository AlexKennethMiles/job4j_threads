package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    private final int capacity = 5;
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(capacity);

    @Test
    public void whenOneOfferedOnePolled() throws InterruptedException {
        AtomicInteger polled = new AtomicInteger();
        Thread consumer = new Thread(() -> {
            try {
                polled.set(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            for (int i = 0; i < capacity; i++) {
                Integer value = -1;
                try {
                    value = queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(value);
                polled.set(value);
            }
        }, "Consumer");
        Thread producer = new Thread(() -> {
            for (int i = 0; i < capacity; i++) {
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
            try {
                polled.set(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer");
        Thread producer = new Thread(() -> {
            for (int i = 0; i < capacity; i++) {
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
            for (int i = 0; i < capacity; i++) {
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
                Integer value = -1;
                try {
                    value = queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        List<Integer> rsl = new ArrayList<>();
        Thread producer = new Thread(() -> {
            IntStream.range(0, 4).forEach(
                    queue::offer
            );
        }, "Producer");
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                try {
                    rsl.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer");
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(rsl).isEqualTo(List.of(0, 1, 2, 3));
    }
}
