package ru.job4j.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    public void when3x3MatrixSynchroTask() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] exp = new RolColSum.Sums[]{
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void when3x3MatrixAsynchTask() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] exp = new RolColSum.Sums[]{
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void when1x1MatrixSynchro() {
        int[][] matrix = {
                {1}
        };
        RolColSum.Sums[] exp = new RolColSum.Sums[]{
                new RolColSum.Sums(1, 1)
        };
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void when1x1MatrixAynch() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1}
        };
        RolColSum.Sums[] exp = new RolColSum.Sums[]{
                new RolColSum.Sums(1, 1)
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl).isEqualTo(exp);
    }


    @Test
    public void whenEmptyMatrix() {
        int[][] matrix = {{}};
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                RolColSum.sum(matrix)
        );
    }
}
