package ru.job4j.asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolComSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    /**
     * Метод последовательно проходит по всему входному массиву matrix
     * и вычисляет сумму чисел для каждого столбца и каждой строки
     *
     */

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int sumRow = 0;
        int sumCol = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(sumRow);
            sums[i].setColSum(sumCol);
            sumRow = 0;
            sumCol = 0;
        }
        return sums;
    }

    /**
     * Метод вычисляет сумму чисел для каждого столбца и каждой строки
     * при помощи асинхронного метода countSum
     */

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            Integer[] result = countSum(i, matrix).get();
            sums[i].setRowSum(result[0]);
            sums[i].setColSum(result[1]);
        }
        return sums;
    }

    /**
     * Метод вычисляет сумму чисел строки и столбца матрицы под
     * определенным индексом
     *
     * @param index - индекс строки массива
     * @param matrix - входной массив
     * @return result[] массив из двух значений:
     * result[0] - сумма чисел строки под номером index
     * result[1] - сумма чисел столбца под номером index
     */

    public static CompletableFuture<Integer[]> countSum(
            int index, int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {
            Integer[] result = new Integer[2];
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < matrix[index].length; j++) {
                sumRow += matrix[index][j];
                sumCol += matrix[j][index];
            }
            result[0] = sumRow;
            result[1] = sumCol;
            return result;
        });
    }


    public static void main(String[] args) {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] result = sum(array);

        for (int i = 0; i < result.length; i++) {
            System.out.println("row sum " + i + " " + result[i].rowSum);
            System.out.println("col sum " + i + " " + result[i].colSum);
        }
    }

}
