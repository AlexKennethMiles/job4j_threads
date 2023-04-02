package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    @Test
    void whenAddIsTrue() {
        Cache cache = new Cache();
        boolean rsl = cache.add(new Base(1, 0));
        assertThat(rsl).isTrue();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 0));
    }

    @Test
    void whenDoesNotAdd() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        boolean rsl = cache.add(new Base(1, 2));
        assertThat(rsl).isFalse();
    }

    @Test
    void whenAddAndUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("Andrey");
        cache.add(base1);
        Base base2 = new Base(1, 1);
        base2.setName("Denis");
        boolean rsl = cache.update(base2);
        assertThat(rsl).isTrue();
        assertThat(cache.get(1).getName()).isEqualTo("Denis");
        assertThat(cache.get(1).getVersion()).isEqualTo(2);
    }

    @Test
    void whenDeleteAndGet() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        assertThat(cache.get(base1.getId())).isEqualTo(new Base(1, 1));
        cache.delete(base1);
        assertThat(cache.get(base1.getId())).isNull();
        assertThat(cache.get(base2.getId())).isEqualTo(new Base(2, 1));
    }
}
