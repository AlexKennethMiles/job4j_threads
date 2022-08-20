package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    public void whenProcedureMethod() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[] rsl = new int[6];
        int[] exp = {6, 15, 24, 12, 15, 18};
        RolColSum.Sums[] processedMatrix = RolColSum.sum(matrix);
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[i] = processedMatrix[i].getRowSum();
        }
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[processedMatrix.length + i] = processedMatrix[i].getColSum();
        }
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void whenAsyncMethod() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[] rsl = new int[6];
        int[] exp = {6, 15, 24, 12, 15, 18};
        RolColSum.Sums[] processedMatrix = RolColSum.asyncSum(matrix);
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[i] = processedMatrix[i].getRowSum();
        }
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[processedMatrix.length + i] = processedMatrix[i].getColSum();
        }
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void whenMatrixOneByOne() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1}
        };
        int[] rsl = new int[2];
        int[] exp = {1, 1};
        RolColSum.Sums[] processedMatrix = RolColSum.asyncSum(matrix);
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[i] = processedMatrix[i].getRowSum();
        }
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[processedMatrix.length + i] = processedMatrix[i].getColSum();
        }
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void whenEmptyMatrix() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {}
        };
        int[] rsl = new int[]{};
        int[] exp = {};
        RolColSum.Sums[] processedMatrix = RolColSum.asyncSum(matrix);
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[i] = processedMatrix[i].getRowSum();
        }
        for (int i = 0; i < processedMatrix.length; i++) {
            rsl[processedMatrix.length + i] = processedMatrix[i].getColSum();
        }
        assertThat(rsl).isEqualTo(exp);
    }
}
