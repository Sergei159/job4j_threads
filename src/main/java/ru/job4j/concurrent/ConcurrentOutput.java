package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
        && second.getState() != Thread.State.TERMINATED ) {
            System.out.println(first.getState() + " First\n");
            System.out.println(second.getState() + " Second\n");
        }
        System.out.println(first.getState() + " First");
        System.out.println(second.getState() + " Second");
        System.out.println("Threads \"first\" and \"second\" are terminated");
    }

}
