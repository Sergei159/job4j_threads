package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void when600() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                () -> {
            for (int i = 0; i < 50; i++) {
                casCount.increment();
            }
        });
        first.start();
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 550; i++) {
                        casCount.increment();
                    }
                });

        second.start();
        first.join();
        second.join();
        assertEquals(casCount.get(), 600);
    }

    @Test
    public void whenOneThread() throws InterruptedException {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        assertEquals(casCount.get(), 2);
    }

    @Test
    public void whenIsNull() throws InterruptedException {
        CASCount casCount = new CASCount();
        assertEquals(casCount.get(), 0);
    }

}