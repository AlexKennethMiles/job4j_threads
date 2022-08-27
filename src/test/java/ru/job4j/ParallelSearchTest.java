package ru.job4j;

import org.junit.jupiter.api.Test;
import ru.job4j.email.User;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {
    @Test
    public void whenFoundUserInNineElements() {
        User[] users = new User[]{
                new User("Nick", "nick@gmail.com"),
                new User("Thomas", "thomas@gmail.com"),
                new User("Bob", "bob@gmail.com"),
                new User("Marian", "marian@gmail.com"),
                new User("Jack", "jack@gmail.com"),
                new User("Paul", "paul@gmail.com"),
                new User("Eddie", "eddie@gmail.com"),
                new User("Melissa", "melissa@gmail.com"),
                new User("Michael", "michael@gmail.com")
        };
        User searchUser = new User("Michael", "michael@gmail.com");
        int rsl = ParallelSearch.search(users, searchUser);
        assertThat(rsl).isEqualTo(8);
    }

    @Test
    public void whenNotFoundUserInNineElements() {
        User[] users = new User[]{
                new User("Nick", "nick@gmail.com"),
                new User("Thomas", "thomas@gmail.com"),
                new User("Bob", "bob@gmail.com"),
                new User("Marian", "marian@gmail.com"),
                new User("Jack", "jack@gmail.com"),
                new User("Paul", "paul@gmail.com"),
                new User("Eddie", "eddie@gmail.com"),
                new User("Melissa", "melissa@gmail.com"),
                new User("Michael", "michael@gmail.com")
        };
        User searchUser = new User("Karen", "Karen@gmail.com");
        int rsl = ParallelSearch.search(users, searchUser);
        assertThat(rsl).isEqualTo(-1);
    }

    @Test
    public void whenFoundIntegerInNineElements() {
        Integer[] users = new Integer[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8
        };
        int rsl = ParallelSearch.search(users, 8);
        assertThat(rsl).isEqualTo(8);
    }

    @Test
    public void whenNotFoundIntegerInNineElements() {
        Integer[] users = new Integer[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8
        };
        int rsl = ParallelSearch.search(users, 10);
        assertThat(rsl).isEqualTo(-1);
    }

    @Test
    public void whenFoundUserInFiftyElements() {
        User[] users = new User[]{
                new User("Nick", "nick@gmail.com"),
                new User("Thomas", "thomas@gmail.com"),
                new User("Bob", "bob@gmail.com"),
                new User("Marian", "marian@gmail.com"),
                new User("Jack", "jack@gmail.com"),
                new User("Paul", "paul@gmail.com"),
                new User("Eddie", "eddie@gmail.com"),
                new User("Melissa", "melissa@gmail.com"),
                new User("Leroy", "leroy@gmail.com"),
                new User("Pauline", "pauline@gmail.com"),
                new User("Joanne", "joanne@gmail.com"),
                new User("Bill", "bill@gmail.com"),
                new User("Daniel", "daniel@gmail.com"),
                new User("Betty", "betty@gmail.com"),
                new User("Michael", "michael@gmail.com")
        };
        User searchUser = new User("Michael", "michael@gmail.com");
        int rsl = ParallelSearch.search(users, searchUser);
        assertThat(rsl).isEqualTo(14);
    }

    @Test
    public void whenFoundIntegerInFiftyElements() {
        Integer[] users = new Integer[15];
        for (int i = 0; i < 15; i++) {
            users[i] = i;
        }
        int rsl = ParallelSearch.search(users, 0);
        assertThat(rsl).isEqualTo(0);
    }

    @Test
    public void whenFoundIntegerInHundredElements() {
        Integer[] users = new Integer[100];
        for (int i = 0; i < 100; i++) {
            users[i] = i;
        }
        int rsl = ParallelSearch.search(users, 99);
        assertThat(rsl).isEqualTo(99);
    }

    @Test
    public void whenFoundIntegerInThousandElements() {
        Integer[] users = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            users[i] = i;
        }
        int rsl = ParallelSearch.search(users, 0);
        assertThat(rsl).isEqualTo(0);
    }

    @Test
    public void whenFoundIntegerInTenThousandElements() {
        Integer[] users = new Integer[10_000];
        for (int i = 0; i < 10_000; i++) {
            users[i] = i;
        }
        int rsl = ParallelSearch.search(users, 5_721);
        assertThat(rsl).isEqualTo(5_721);
    }

    @Test
    public void whenArrayLengthIsAPrimeNumber() {
        Integer[] users = new Integer[9_604];
        for (int i = 0; i < 9_604; i++) {
            users[i] = i;
        }
        int rsl = ParallelSearch.search(users, 1_936);
        assertThat(rsl).isEqualTo(1_936);
    }
}
