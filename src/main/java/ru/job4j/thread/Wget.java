package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String destination;
    private final long speed;


    public Wget(String url, String destination, long speed) {
        this.url = url;
        this.destination = destination;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(100);
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                if (speed > 1024 / time) {
                    Thread.sleep(speed - 1024 / time);
                }
                startTime = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String destination = args[1];
        long speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, destination, speed));
        wget.start();
        wget.join();
    }
}
