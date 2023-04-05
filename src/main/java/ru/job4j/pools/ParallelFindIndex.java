package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Реализован класс для поиска индекса элемента массива обобщённого типа.
 * Для этого был использован рекурсивный алгоритм через класс ForkJoinPool.
 * Массив сначала делиться (fork) на равные части до тех пор, пока делить будет
 * нЕчего (массив из одного элемента), и тогда начинается процесс сборки (join).
 * На каждой итерации сборки происходит поиск нужного элемента с учётом сдвига
 * при разделении.
 *
 * @param <T> - обобщённый тип элементов массива (статического)
 */
public class ParallelFindIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T el;
    private final int from;
    private final int to;

    public ParallelFindIndex(T[] array, T el, int from, int to) {
        this.array = array;
        this.el = el;
        this.from = from;
        this.to = to;
    }

    public static <T> int sort(T[] array, T el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndex<>(array, el, 0, array.length - 1));
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return simpleSort();
        }
        int mid = (from + to) / 2;
        ParallelFindIndex<T> left = new ParallelFindIndex<>(array, el, from, mid);
        ParallelFindIndex<T> right = new ParallelFindIndex<>(array, el, mid + 1, to);
        left.fork();
        right.fork();
        return Math.max(left.join(), right.join());
    }

    private int simpleSort() {
        for (int i = from; i < to; i++) {
            if (Objects.equals(el, array[i])) {
                return i;
            }
        }
        return -1;
    }
}
