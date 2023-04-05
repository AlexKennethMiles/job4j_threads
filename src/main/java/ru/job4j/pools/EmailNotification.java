package ru.job4j.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Создан класс-каркас для рассылки сообщений на электронную почту через ThreadPool
 * на базе ExecutorService.
 */
public class EmailNotification {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final ExecutorService pool = Executors.newFixedThreadPool(size);

    public void toMail(User user) throws InterruptedException {
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s", user.getUsername(), email);
        String body = String.format("Add a new event to %s", email);
        pool.submit(() -> send(subject, body, email));
        close();
    }

    public void send(String subject, String body, String email) {

    }

    public void close() throws InterruptedException {
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(100);
        }
    }
}
