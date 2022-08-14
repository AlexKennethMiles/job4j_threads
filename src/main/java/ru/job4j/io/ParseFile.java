package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile implements ReadFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content(data -> true);
    }

    public String getContentWithoutUnicode() {
        return content(data -> data < 0x80);
    }

    @Override
    public String content(Predicate<Integer> filter) {
        String output = "";
        try (BufferedInputStream bif = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = bif.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
