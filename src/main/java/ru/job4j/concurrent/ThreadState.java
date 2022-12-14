package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        System.out.println("Главная нить: " + Thread.currentThread().getState());
        Thread first = new Thread(
                () -> {
                    System.out.println("This is " + Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println("This is " + Thread.currentThread().getName());
                }
        );
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + first.getState());
        first.start();
        second.start();
        System.out.println("=== Начало работы обеих нитей ===");
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + " " + first.getState());
            System.out.println(second.getName() + " " + first.getState());
        }
        System.out.println("=== Завершение работы обеих нитей ===");
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + second.getState());
        System.out.println("--- Обе нити завершили свою работу ---");
        System.out.println("Главная нить: " + Thread.currentThread().getState());
    }
}
