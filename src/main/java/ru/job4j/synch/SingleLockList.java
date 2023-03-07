package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Программа поддерживает итератор для абстрактного списка List
 * в многопоточной среде за счёт synchronized методов, а также
 * работы итератора с копией данных, во избежание
 * ConcurrentModificationException, а также visibility problem.
 * По сути: итератор создаётся для той структуры данных, на которой
 * он был вызван. Если структура изменилась после этого, итератор
 * работает с первоначальной версией (копией) этой структуры.
 */
@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private synchronized List<T> copy(List<T> origin) {
        return origin.stream().collect(Collectors.toList());
    }
}
