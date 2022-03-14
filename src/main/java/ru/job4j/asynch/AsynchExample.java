package ru.job4j.asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsynchExample {

    /**
     * На выбор:
     *  runAsyncExample();
     *  supplyAsyncExample();
     *  thenRunExample();
     *  thenAcceptExample()
     *  thenApplyExample();
     *  thenComposeExample();
     *  thenCombineExample();
     *  allOfExample();
     */

    public static void main(String[] args) throws Exception {
        anyOfExample();
    }


    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     *
     * Пример runAsync()
     */

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    /**
     *
     * Пример supplyAsync()
     */

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }


    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * Этот метод ничего не возвращает,
     * а позволяет выполнить задачу типа Runnable после выполнения асинхронной задачи.
     */

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын: я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын: Я помыл руки");
        });
        iWork();
    }

    /**
     * В отличие от thenRun(), этот метод имеет доступ к результату CompletableFuture.
     */

    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     *Этот метод принимает Function.
     * Также имеет доступ к результату. Как раз благодаря этому,
     * мы можем произвести преобразование полученного результата.
     * Однако результат преобразования станет доступным только при вызове get()
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    /**
     * Данный метод используется, если действия зависимы.
     * Т.е. сначала должно выполниться одно, а только потом другое.
     * Например, вам принципиально, чтобы сын сначала выбросил мусор,
     * а только потом сходил за молоком.
     * В ситуации можно записать так
     */
    public static void thenComposeExample() throws Exception {
        CompletableFuture<Void> result = buyProduct("Молоко").thenCompose(a -> goToTrash());
        iWork();
    }

    /**
     * Данный метод используется, если действия могут быть выполнены независимо друг от друга.
     * Причем в качестве второго аргумента, нужно передавать BiFunction – функцию,
     * которая преобразует результаты двух задач во что-то одно.
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    /**
     * Это метод возвращает ComputableFuture<Void>, при этом обеспечивает выполнение всех задач.
     * Например, вы зовете всех членов семью к столу. Надо дождаться пока все помоют руки
     */
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * Этот метод возвращает ComputableFuture<Object>.
     * Результатом будет первая выполненная задача.
     * На том же примере мы можем, например, узнать, кто сейчас моет руки.
     * Результаты каждого запуска будут различаться.
     */

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

}
