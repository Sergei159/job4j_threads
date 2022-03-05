package ru.job4j.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public boolean emailTo(User user) {
        return true;
    }

    public void close() {

    }
}
