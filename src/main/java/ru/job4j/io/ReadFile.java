package ru.job4j.io;

import java.util.function.Predicate;

public interface ReadFile {
    String content(Predicate<Integer> filter);
}
