package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelFindIndexTest {
    @Test
    public void whenArrayOfInteger() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        ParallelFindIndex<Integer> search = new ParallelFindIndex<>(
                array,
                137,
                0,
                array.length - 1
        );
        int rsl = search.compute();
        assertThat(rsl).isEqualTo(136);
    }

    @Test
    public void whenArrayOfUser() {
        User[] array = new User[]{
                new User("Alex", "alex@email.com"),
                new User("Petr", "petr@gmail.com"),
                new User("Michail", "mihail@yandex.ru"),
                new User("Nikolai", "nikoli@box.net"),
                new User("Jane", "Jane@box.net"),
                new User("Anita", "Anita@box.net"),
                new User("Brittany", "Brittany@box.net"),
                new User("Gloria", "Gloria@box.net"),
                new User("Rose", "Rose@box.net"),
                new User("Ida", "Ida@box.net"),
        };
        ParallelFindIndex<User> search = new ParallelFindIndex<>(
                array,
                new User("Gloria", "Gloria@box.net"),
                0,
                array.length - 1
        );
        int rsl = search.compute();
        assertThat(rsl).isEqualTo(7);
    }

    @Test
    public void whenVeryBigArrayOfInteger() {
        Integer[] array = new Integer[1_000_000];
        for (int i = 0; i < 1_000_000; i++) {
            array[i] = i + 1;
        }
        ParallelFindIndex<Integer> search = new ParallelFindIndex<>(
                array,
                990_593,
                0,
                array.length - 1
        );
        int rsl = search.compute();
        assertThat(rsl).isEqualTo(990_592);
    }

    @Test
    public void whenElementNotFound() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        ParallelFindIndex<Integer> search = new ParallelFindIndex<>(
                array,
                5432,
                0,
                array.length - 1
        );
        int rsl = search.compute();
        assertThat(rsl).isEqualTo(-1);
    }
}
