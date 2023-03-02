package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            FileOutputStream out = new FileOutputStream(url.substring(url.lastIndexOf('/') + 1));
            byte[] dataBuff = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuff, 0, 1024)) != -1) {
                downloadData += bytesRead;
                out.write(bytesRead);
                if (downloadData >= speed) {
                    if (System.currentTimeMillis() - start < 1000) {
                        Thread.sleep(1000 - (System.currentTimeMillis() - start));
                    }
                    start = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static boolean validateArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong args argument...");
        }
        try {
            URL url = new URL(args[0]);
            url.getContent();
        } catch (IOException e) {
            System.out.println("Incorrect URL address...");
        }
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect speed value...");
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
