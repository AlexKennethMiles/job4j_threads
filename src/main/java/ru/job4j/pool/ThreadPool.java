package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private static final int QUEUE_SIZE = 5;
    private final List<Thread> threads = new LinkedList<>();
    private final int poolSize = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(QUEUE_SIZE);

    public ThreadPool() {
        for (int i = 0; i < poolSize; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
            threads.get(i).start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.stream().filter(Thread::isAlive).forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPool.work(new MyRunnable());
        }
        threadPool.shutdown();
    }
}
