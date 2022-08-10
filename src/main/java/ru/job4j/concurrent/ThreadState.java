package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        System.out.println("Главная нить: " + Thread.currentThread().getState());
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + first.getState());
        first.start();
        second.start();
        System.out.println("=== Начало работы обеих нитей ===");
        while (first.getState() == Thread.State.RUNNABLE
                || second.getState() == Thread.State.RUNNABLE) {
            System.out.println(first.getName() + " " + first.getState());
            System.out.println(second.getName() + " " + second.getState());
        }
        System.out.println("=== Завершение работы обеих нитей ===");
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + second.getState());
        System.out.println("--- Обе нити завершили свою работу ---");
        System.out.println("Главная нить: " + Thread.currentThread().getState());
    }
}
