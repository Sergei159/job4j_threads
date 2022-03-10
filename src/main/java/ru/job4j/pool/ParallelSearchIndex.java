package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static ru.job4j.pool.ParallelMergeSort.sort;

/**
 * Класс реализует поиск элемента по индексу при помощи
 * класса java.util.concurrent.RecursiveTask
 */

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private static final int BORDER = 10;

    private final T[] array;
    private final T valueToFind;
    private  final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T valueToFind, int from, int to) {
        this.array = array;
        this.valueToFind = valueToFind;
        this.from = from;
        this.to = to;
    }

    public static int indexOf(Object[] array, Object valueToFind) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndex<>(array, valueToFind,  0, array.length - 1));
    }

    /**
     * Метод реализует линейный поиск
     *
     */
    private int linearSearch() {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == valueToFind) {
                result = i;
                break;
            }
        }
        return result;
    }


    /**
     * Метод реализует бинарный поиск,используя рекурсию
     * @return
     */
    @Override
    protected Integer compute() {
        if (to - from < BORDER) {
            return linearSearch();
        } else {
            int middle = (from + to) / 2;
            ParallelSearchIndex<T> left = new ParallelSearchIndex<>(
                    array, valueToFind, to, middle);
            ParallelSearchIndex<T> right = new ParallelSearchIndex<>(
                    array, valueToFind, to, middle);
            left.fork();
            right.fork();
            int leftPart = left.join();
            int rightPart = right.join();
            return leftPart != -1 ? leftPart : rightPart;
        }
    }
}
