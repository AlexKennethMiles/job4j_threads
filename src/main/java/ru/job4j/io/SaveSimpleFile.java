package ru.job4j.io;

import java.io.*;

public final class SaveSimpleFile implements SaveFile {
    private final File file;

    public SaveSimpleFile(File file) {
        this.file = file;
    }

    @Override
    public void saveContent(String content) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                bos.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
