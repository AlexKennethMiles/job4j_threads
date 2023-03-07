package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Исправлены ошибки:
 * 1) Потокобезопасность - класс стал Immutable, методы synchronized;
 * 2) IO ошибки - добавлены try-with-resources;
 * 3) Нарушение Single Responsibility Principle - класс разделён на два, читающий и записывающий;
 * 4) Повторение кода в методе getContent - применение шаблона Strategy;
 * [5] Конкатенация String через += - замена на {@link StringBuilder#append(char)}.
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContent() {
        return content(data -> true);
    }

    public synchronized String getContentWithUnicode() {
        return content(data -> data < 0x80);
    }

}
