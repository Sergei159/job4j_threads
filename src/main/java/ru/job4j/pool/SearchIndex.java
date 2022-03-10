package ru.job4j.pool;

import static ru.job4j.pool.ParallelMergeSort.sort;

/**
 * Класс реализует поиск элемента по индексу
 */

public class SearchIndex {
    private static final int BORDER = 10;

    public static int indexOf(int[] array, int valueToFind) {
        return (array.length > BORDER)
                ? binarySearch(array, valueToFind)
                : linearSearch(array, valueToFind);

    }

    /**
     * Метод реализует линейный поиск
     *
     */
    private static int linearSearch(int[] array, int valueToFind) {
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
     * Метод реализует бинарный поиск.
     * Изначально для этого исходный массив сортируется,
     * используя многопоточный метод sort класса ParallelMergeSort
     *
     */
    private static int binarySearch(int[] array, int valueToFind) {
        int result = -1;
        int[] sortedArray = sort(array);
        int middle;
        int start = 0;
        int end = sortedArray.length - 1;
        while (start <= end) {
            middle = start + (end - start) / 2;
            if (sortedArray[middle] == valueToFind) {
                result = middle;
                break;
            }
            if (sortedArray[middle] > valueToFind) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
       return result;
    }
}
