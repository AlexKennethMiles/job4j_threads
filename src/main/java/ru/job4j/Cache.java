package ru.job4j;

public final class Cache {
    private static Cache cache;

    /**
     * Исправили ошибку атомарности, синхронизировав метод (монитор - класс Cache).
     */
    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
