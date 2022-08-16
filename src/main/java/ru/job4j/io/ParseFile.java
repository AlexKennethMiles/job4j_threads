package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile implements ReadFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(data -> data < 0x80);
    }

    @Override
    public synchronized String content(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream bif = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = bif.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
