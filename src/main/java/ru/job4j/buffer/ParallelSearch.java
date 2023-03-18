package ru.job4j.buffer;

import ru.job4j.queue.SimpleBlockingQueue;

/**
 * Когда поставщик (producer) завершит свою работу, потребитель (consumer)
 * после выполнения своей работы перейдёт в блокирующее состояние через
 * метод {@link Object#wait()}, но выйдет из него выбросив исключение
 * InterruptException, так как в теле метода main, в нити потребителя
 * будет вызван метод {@link Thread#interrupt()}.
 * В блоке try-cath будет поймано исключение (снимится блокировка из-за wait())
 * и в catch снова будет поднят флаг остановки потока через {@link Thread#interrupt()}.
 * Это позволяет завершить работу потребителя, когда новых задач не ожидается,
 * и, следовательно, ждать их не нужно.
 */
public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        producer.join();
        consumer.interrupt();
    }
}
