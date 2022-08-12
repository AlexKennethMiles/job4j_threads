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
        long start = System.currentTimeMillis();
        try (BufferedInputStream bf = new BufferedInputStream(new URL(url).openStream())) {
            String fileName = "temp_".concat(url.substring(url.lastIndexOf('/') + 1));
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buff = new byte[1024];
            int downloadData = 0;
            long period;
            int bytesRead;
            while ((bytesRead = bf.read(buff)) != -1) {
                fos.write(buff, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    period = System.currentTimeMillis() - start;
                    if (period < 1000) {
                        Thread.sleep(1000 - period);
                    }
                    start = System.currentTimeMillis();
                    downloadData = 0;
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
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод валидирует входные параметры (корректность ссылки и значение скорости закачки в Б/сек),
     * после чего запускает поток на считывание файла по ссылке. Если фактическая скорость закачки
     * выше заявленной, то поток засыпает на 1 секунду (превышена ожидаемая скорость загрузки).
     *
     * @param args — в args[0] положили ссылку:
     *             proof.ovh.net/files/10Mb.dat
     *             в args[1] положили значение скорости равное 1048576 байт/сек или 1 МБ/с
     *             с такими входными данными, файл скачается за 10 секунд.
     * @throws InterruptedException в случае остановки метода {@link Wget#run()} через try-catch
     *                              будет проброшен флаг interrupted, чтобы уведомить об этом клиентскую часть программы.
     */
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Аргументы для запуска программы заданы не полностью!");
        }
        String url = args[0];
        System.out.println("Is URL valid: " + isValidURL(url));
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
