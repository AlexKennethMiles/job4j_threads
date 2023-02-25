package ru.job4j.threads;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("Some work in FIRST thread...")
        );
        Thread second = new Thread(
                () -> System.out.println("Some work in SECOND thread...")
        );
        System.out.println(first.getName() + ' ' + first.getState());
        System.out.println(second.getName() + ' ' + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                && second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + ' ' + first.getState());
            System.out.println(second.getName() + ' ' + second.getState());
        }
        System.out.println(first.getName() + ' ' + first.getState());
        System.out.println(second.getName() + ' ' + second.getState());
        System.out.println("All threads have terminated");
    }
}
