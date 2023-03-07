package ru.job4j.synch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

public class SingleLockListTest {

    @Test
    public void whenIt() {
        var init = new ArrayList<Integer>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        list.add(1);
        var it = list.iterator();
        list.add(2);
        assertThat(1).isEqualTo(it.next());
    }

    @Test
    public void whenAdd() throws InterruptedException {
        var init = new ArrayList<Integer>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        Thread first = new Thread(() -> list.add(2));
        Thread second = new Thread(() -> list.add(1));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).isEqualTo(Set.of(1, 2));
    }

    @Test
    public void whenModifyAfterStartIterator() throws InterruptedException {
        var init = new ArrayList<Integer>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        Thread first = new Thread(() -> {
            list.add(1);
            list.add(2);
        });
        Thread second = new Thread(() -> {
            list.add(3);
            list.add(4);
        });
        var itBefore = list.iterator();
        first.start();
        second.start();
        first.join();
        second.join();
        var itAfter = list.iterator();
        List<Integer> rsl = new ArrayList<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(itBefore.hasNext()).isFalse();
        assertThat(itAfter.next()).isEqualTo(1);
        assertThat(itAfter.next()).isEqualTo(2);
        assertThat(itAfter.next()).isEqualTo(3);
        assertThat(itAfter.next()).isEqualTo(4);
        assertThat(itAfter.hasNext()).isFalse();
        assertThat(rsl).isEqualTo(List.of(1, 2, 3, 4));
    }
}
