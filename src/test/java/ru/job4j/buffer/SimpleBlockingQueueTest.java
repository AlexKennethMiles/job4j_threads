package ru.job4j.buffer;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenProducerFillsTheQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        Queue<Integer> rsl = new LinkedList<>();
        int count = 10;
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < count; i++) {
                        try {
                            sbq.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = count; i > 0; i--) {
                        try {
                            rsl.add(sbq.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rsl).isEqualTo(new LinkedList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }

    @Test
    public void whenConsumerTriesToTakeElButEmptyQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        Queue<Integer> rsl = new LinkedList<>();
        int count = 10;
        Thread producer = new Thread(
                () -> System.out.println("No offer")
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = count; i > 0; i--) {
                        try {
                            rsl.add(sbq.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        assertThat(rsl).isEqualTo(new LinkedList<Integer>(List.of()));
    }

    @Test
    public void whenProducerFillsTheQueueButFullQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        Queue<Integer> rsl = new LinkedList<>();
        int count = 5;
        for (int i = 0; i < count; i++) {
            sbq.offer(i);
        }
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < count; i++) {
                        try {
                            sbq.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = count; i > 0; i--) {
                        try {
                            rsl.add(sbq.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rsl).isEqualTo(new LinkedList<>(List.of(0, 1, 2, 3, 4)));
    }
}
