package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private static final char[] PROCESS = new char[]{'-', '\\', '|', '/'};

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2500);
        progress.interrupt();
    }

    @Override
    public void run() {
        int index = 0;
        while (!Thread.currentThread().interrupted()) {
            System.out.print("\rLoad: " + PROCESS[index]);
            index++;
            if (PROCESS.length <= index) {
                index = 0;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
