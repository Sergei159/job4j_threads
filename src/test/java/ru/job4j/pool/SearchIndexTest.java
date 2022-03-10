package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SearchIndexTest {

    @Test
    public void whenLinearSearch() {
        Integer[] array = new Integer[] {14, 46, 32, 67, 3, 11, 77, 87, 44, 95};
        int value = 87;
        int expectedIndex = 7;
        assertThat(ParallelSearchIndex.indexOf(array, value), is(expectedIndex));
    }

    @Test
    public void whenLinearSearchIsNotFound() {
        Integer[] array = new Integer[] {14, 46, 32, 67, 3, 11, 77, 87, 44, 95};
        int value = 100;
        int expectedIndex = -1;
        assertThat(ParallelSearchIndex.indexOf(array, value), is(expectedIndex));
    }

    @Test
    public void whenBinarySearch() {
        Integer[] array = new Integer[] {
                14, 46, 32, 67, 3, 11, 77, 87, 44, 95, 355, 22, 15, 67, 433, 11
        };
        int value = 3;
        int expectedIndex = 4;
        assertThat(ParallelSearchIndex.indexOf(array, value), is(expectedIndex));
    }

    @Test
    public void whenBinarySearchIsNotFound() {
        Integer[] array = new Integer[] {
                14, 46, 32, 67, 3, 11, 77, 87, 44, 95, 355, 22, 15, 67, 433, 11
        };
        int value = 100;
        int expectedIndex = -1;
        assertThat(ParallelSearchIndex.indexOf(array, value), is(expectedIndex));
    }

}