package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Создан класс блокирующей очереди.
 * методы {@link SimpleBlockingQueue#offer(Object)} и {@link SimpleBlockingQueue#poll()}
 * настроены на работу в многопоточной среде для нитей двух ролей:
 * 1) Producer - создаёт элементы очереди или ждёт, если очередь заполнена;
 * 2) Consumer - забирает элемент очереди или ждёт, если очередь пуста.
 *
 * @param <T> - тип элемента очереди.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void offer(T value) {
        while (queue.size() == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.offer(value);
        notifyAll();

    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
