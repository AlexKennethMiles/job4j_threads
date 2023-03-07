package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Изучены аннотации библиотеки jcip.
 * Протестировано их влияние на код потокобезопасной программы.
 */

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public synchronized int increment() {
        return value++;
    }

    public synchronized int get() {
        return value;
    }
}