package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 *
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
 * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 *
 * Пауза должна вычисляться, а не быть константой.
 */

public class Wget implements Runnable {
    private final String url;
    private final String destination;
    private final long speed;


    public Wget(String url, String destination, long speed) {
        this.url = url;
        this.destination = destination;
        this.speed = speed;
    }

    /**
     * int downloadData - кол-во скаченных байт.
     * как только downloadData достигает значение speed
     * проверяется время от начала скачивания до этого условия.
     * Если время меньше секунды, то вызываем sleep у потока на разницу между
     * переменной time и 1 секундой.
     * После паузы обнуляем downloadData
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    if (time < 1000) {
                        Thread.sleep(1000 - time);
                        downloadData = 0;
                        startTime = System.currentTimeMillis();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalStateException("String[] args must contain three arguments: "
                    + "url, name of target file, speed of downloading, b/s");
        }
        String url = args[0];
        String destination = args[1];
        long speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, destination, speed));
        wget.start();
        wget.join();
    }
}
