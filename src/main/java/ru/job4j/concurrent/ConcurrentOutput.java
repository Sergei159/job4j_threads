package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        int count = 10;
        for (int i = 0; i < count ; i++) {
             Thread thread = new Thread(
                    () -> System.out.println(Thread.currentThread().getName())
            );
             thread.start();
        }
        System.out.println(Thread.currentThread().getName());
    }
}