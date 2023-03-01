package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private char[] process = new char[]{'-', '\\', '|', '/'};

    @Override
    public void run() {
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (i >= 4) {
                    i = 0;
                }
                System.out.print("\rLoading: " + process[i++]);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread loading = new Thread(new ConsoleProgress());
        loading.start();
        Thread.sleep(5000);
        loading.interrupt();
    }
}
