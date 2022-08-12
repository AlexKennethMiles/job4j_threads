package ru.job4j;

public final class Cache {
    private static Cache cache;

    /**
     * Метод модифицирован синхронизацией, что ограничивает возможность обращения к
     * этому методу одного экземпляра класса двумя (или более) разными нитями.
     * Таким образом, выполняется условие атомарности.
     * @return - возвращает экземпляр класса Cache
     */
    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
