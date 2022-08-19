package ru.job4j;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final List<T> array;
    private final T value;

    public ParallelSearch(List<T> array, T value) {
        this.array = array;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (array.size() < 10) {
            int index = 0;
            for (T t : array) {
                if (t.equals(value)) {
                    return index;
                }
                index++;
            }
            return -1;
        }
        int mid = array.size() / 2;
        ParallelSearch<T> leftSide = new ParallelSearch<>(array.subList(0, mid), value);
        ParallelSearch<T> rightSide = new ParallelSearch<>(array.subList(mid, array.size()), value);
        leftSide.fork();
        rightSide.fork();
        Integer left = leftSide.join();
        Integer right = rightSide.join();
        return right == -1 ? left : (right + mid);
    }
}
