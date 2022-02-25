package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int count = 0;
        char[] process = {'\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r load: " + process[count]);
                count++;
                if (count > process.length - 1) {
                    count = 0;
                }
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3000);
        progress.interrupt();
    }
}
