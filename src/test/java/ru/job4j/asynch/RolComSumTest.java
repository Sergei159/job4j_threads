package ru.job4j.asynch;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static ru.job4j.asynch.RolComSum.asyncSum;
import static ru.job4j.asynch.RolComSum.sum;

public class RolComSumTest {

    @Test
    public void whenFirstRowIs6() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = sum(array);
        assertThat(result[0].getRowSum(), is(6));

    }

    @Test
    public void whenSecondRowIs15() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = sum(array);
        assertThat(result[1].getRowSum(), is(15));

    }

    @Test
    public void whenThirdRowIs24() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = sum(array);
        assertThat(result[2].getRowSum(), is(24));

    }

    @Test
    public void whenFirstColIs12() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = sum(array);
        assertThat(result[0].getColSum(), is(12));

    }

    @Test
    public void whenSecondColIs15() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = sum(array);
        assertThat(result[1].getColSum(), is(15));

    }

    @Test
    public void whenThirdColIs18() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = sum(array);
        assertThat(result[2].getColSum(), is(18));

    }

    @Test
    public void whenAsyncFirstRowIs6() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = asyncSum(array);
        assertThat(result[0].getRowSum(), is(6));
    }

    @Test
    public void whenAsyncSecondRowIs15() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = asyncSum(array);
        assertThat(result[1].getRowSum(), is(15));

    }

    @Test
    public void whenAsyncThirdRowIs24() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = asyncSum(array);
        assertThat(result[2].getRowSum(), is(24));

    }

    @Test
    public void whenAsyncFirstColIs12() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = asyncSum(array);
        assertThat(result[0].getColSum(), is(12));

    }

    @Test
    public void whenAsyncSecondColIs15() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = asyncSum(array);
        assertThat(result[1].getColSum(), is(15));

    }

    @Test
    public void whenAsyncThirdColIs18() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolComSum.Sums[] result = asyncSum(array);
        assertThat(result[2].getColSum(), is(18));
    }

}