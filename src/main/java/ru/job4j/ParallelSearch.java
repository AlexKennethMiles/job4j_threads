package ru.job4j;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;

    public ParallelSearch(T[] array, T value) {
        this.array = array;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (array.length < 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(value)) {
                    return i;
                }
            }
            return -1;
        }
        int mid = array.length / 2;
        ParallelSearch<T> leftSide = new ParallelSearch<>(Arrays.copyOfRange(array, 0, mid), value);
        ParallelSearch<T> rightSide = new ParallelSearch<>(Arrays.copyOfRange(array, mid, array.length), value);
        leftSide.fork();
        rightSide.fork();
        Integer left = leftSide.join();
        Integer right = rightSide.join();
        return right == -1 ? left : (right + mid);
    }
}
