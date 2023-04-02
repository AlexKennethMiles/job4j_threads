package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Создан класс-счётчик, безопасный для выполнения в многопоточной среде,
 * за счёт реализации шаблона check-and-swap (CAS).
 * Если в ходе работы метода инкремента две нити прочитают одно и то же значение,
 * то первая нить успеет увеличить и записать изменённую переменную (пройдёт CAS-проверку).
 * А вторая нить не сможет пройти CAS-проверку и заново прочитает уже актуальное значение счётчика.
 * Таким образом, без применения синхронизации обеспечивается корректная работа в многопоточной среде.
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref, count.get() + 1));
    }

    public int get() {
        return count.get();
    }
}
