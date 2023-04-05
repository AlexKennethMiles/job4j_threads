package ru.job4j.pools;

import ru.job4j.cas.CASCount;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализован ThreadPool - класс, в котором имеется несколько нитей (кол-во == полю size)
 * и блокирующая очередь задач. Каждая нить (в многопоточном режиме) забирает (при наличии)
 * задачу из очереди и выполняет её. Нити хранятся в списке threads, после заполнения которого,
 * нити запускаются в работу. После того, как все старые задачи были выполнены, а новые
 * уже не будут поступать, всем нитям из списка передаётся сигнал прерывания и одна за другой
 * нити завершат свою работу и программа успешно выполнится.
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Thread-" + i));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        CASCount count = new CASCount();
        for (int i = 0; i < 100; i++) {
            threadPool.work(count::increment);
            System.out.println("Count value:" + count.get());
        }
        threadPool.shutdown();
    }
}
