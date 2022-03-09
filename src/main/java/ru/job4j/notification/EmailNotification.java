package ru.job4j.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс имитирует отправку почты через ExecutorService
 */

public class EmailNotification {
   private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * отправляет почту с помощью метода send через ExecutorService.submit
     * по формату:
     * subject = Notification {username} to email {email}.
     * body = Add a new event to {username}.
     *
     */
    public void emailTo(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is not found");
        }
        String subject = String.format(
                "subject = Notification %s to email %s.",
                user.getUserName(), user.getEmail()
        );
        String body = String.format(
               "body = Add a new event to %s.",
                user.getUserName()
        );
        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    /**
     * Метод закрывает pool и ждет завершения
     * всех задач
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
