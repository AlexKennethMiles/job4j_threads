package ru.job4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (from - to < 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(value)) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSide = new ParallelSearch<>(array, value, from, mid);
        ParallelSearch<T> rightSide = new ParallelSearch<>(array, value, mid + 1, to);
        leftSide.fork();
        rightSide.fork();
        Integer left = leftSide.join();
        Integer right = rightSide.join();
        return right == -1 ? left : right;
    }

    public static <T> int search(T[] array, T value) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearch<>(array, value, 0, array.length - 1));
    }
}
