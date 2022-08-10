package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
        long start = System.nanoTime();
        try (BufferedInputStream bf = new BufferedInputStream(new URL(url).openStream())) {
            FileOutputStream fos = new FileOutputStream("pom_temp.xml");
            byte[] buff = new byte[1024];
            int currByte;
            while ((currByte = bf.read(buff)) != -1) {
                fos.write(buff, 0, currByte);
                if ((System.nanoTime() - start) < speed * 10000L) {
                    Thread.sleep(1000);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    /**
     * Метод валидирует входные параметры (корректность ссылки и значение скорости закачки в Б/сек),
     * после чего запускает поток на считывание файла по ссылке. Если фактическая скорость закачки меньше
     * заявленной, то поток засыпает на 1 секунду (имитация подгрузки данных).
     *
     * @param args — в args[0] положили ссылку:
     *             https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml
     *             в args[1] положили 100000000 (которое позже будет увеличено в 10000 раз)
     * @throws InterruptedException в случае остановки метода {@link Wget#run()} через try-catch
     * будет проброшен флаг interrupted, чтобы уведомить об этом клиентскую часть программы.
     */
    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        System.out.println("Is URL valid: " + isValidURL(url));
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
