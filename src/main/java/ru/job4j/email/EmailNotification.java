package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public boolean emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String username = user.getUsername();
                String email = user.getEmail();
                String subject = "Notification " + username + " to email " + email + ".";
                String body = "Add a new event to " + username;
                send(subject, body, email);
            }
        });
        return false;
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
