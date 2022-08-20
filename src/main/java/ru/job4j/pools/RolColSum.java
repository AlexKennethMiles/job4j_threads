package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (Sums sums : sum(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})) {
            System.out.println(sums.getRowSum() + " / " + sums.getColSum());
        }
        System.out.println("====");
        for (Sums sums : asyncSum(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})) {
            System.out.println(sums.getRowSum() + " / " + sums.getColSum());
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
            rowSum = 0;
            colSum = 0;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        if (matrix[0].length == 0) {
            return new Sums[]{};
        }
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        int length = matrix.length;
        Sums[] sums = new Sums[length];
        for (int i = 0; i < length; i++) {
            map.put(i, getTask(matrix, i));
        }
        for (int i = 0; i < length; i++) {
            sums[i] = map.get(i).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] array, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            Sums sums = new Sums();
            for (int j = 0; j < array.length; j++) {
                rowSum += array[index][j];
                colSum += array[j][index];
            }
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            return sums;
        });
    }

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
}
