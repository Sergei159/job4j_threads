package ru.job4j.pool;

/**
 * Класс реализует сортировку слиянием с использованием рекурсии
 */
public class MergeSort {

    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    /**
     * Метод сортирует массив методом слияния, используя рекурсию
     * @param array
     * @param from
     * @param to
     * @return
     */
    private static int[] sort(int[] array, int from, int to) {
        if (from == to) {
            return new int[] {array[from] };
        }
        int mid = (from + to) / 2;
        return merge(
                sort(array, from, mid),
                sort(array, mid + 1, to)
        );
    }

    /**
     *  Метод объединения двух отсортированных массивов
     */

    public static int[] merge(int[] left, int[] right) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        int[] result = new int[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) {
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (left[li] <= right[ri]) {
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }

}