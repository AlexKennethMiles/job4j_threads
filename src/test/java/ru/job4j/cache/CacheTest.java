package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {
    @Test
    public void whenSuccessfulAddition() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertThat(cache.get(1)).isEqualTo(base);
    }

    /**
     * Так как {@link Cache#delete(Base)} не возвращает значений, проверяем успешность
     * удаления через метод {@link Cache#add(Base)} — если мы успешно удалили объект,
     * то повторный вызов этого метода вернёт true, потому что до того это место оказалось
     * свободно (из-за нашего удаления). В противном случае метод add() возвращал бы предыдущий
     * объект по ключу в {@link java.util.concurrent.ConcurrentHashMap}.
     */
    @Test
    public void whenSuccessfulDeletion() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base anotherBase = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.add(anotherBase)).isTrue();
    }

    @Test
    public void whenSuccessfulUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "Old Base");
        cache.add(base);
        Base newBase = new Base(1, 0, "NEW Base");
        cache.update(newBase);
        assertThat(cache.get(1)).isEqualTo(new Base(1, 1, "NEW Base"));
    }

    @Test
    public void whenThrowsException() {
        assertThrows(OptimisticException.class, () -> {
            Cache cache = new Cache();
            Base base = new Base(1, 0, "Old Base");
            cache.add(base);
            Base newBase = new Base(1, 1, "NEW Base");
            cache.update(newBase);
        });
    }


}
