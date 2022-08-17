package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (this) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized int getTotal() {
        return total;
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(250_000);
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " is started");
                    while (countBarrier.getCount() < countBarrier.getTotal()) {
                        countBarrier.count();
                    }
                },
                "Master"
        );
        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " is started");
                    System.out.println("Total score: " + countBarrier.getTotal());
                },
                "Slave"
        );
        first.start();
        second.start();
    }
}
