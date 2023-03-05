package ru.job4j;

/**
 * Рассмотрена проблема double check locking при создании singleton.
 * Добавили ключевое слово volatile к полю inst.
 */
public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

}
