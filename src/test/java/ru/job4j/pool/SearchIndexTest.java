package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SearchIndexTest {

    @Test
    public void whenLinearSearch() {
        int[] array = new int[] {14, 46, 32, 67, 3, 11, 77, 87, 44, 95};
        int value = 87;
        int expectedIndex = 7;
        assertThat(SearchIndex.indexOf(array, value), is(expectedIndex));
    }

    @Test
    public void whenLinearSearchIsNotFound() {
        int[] array = new int[] {14, 46, 32, 67, 3, 11, 77, 87, 44, 95};
        int value = 100;
        int expectedIndex = -1;
        assertThat(SearchIndex.indexOf(array, value), is(expectedIndex));
    }

    @Test
    public void whenBinarySearch() {
        int[] array = new int[] {
                14, 46, 32, 67, 3, 11, 77, 87, 44, 95, 355, 22, 15, 67, 433, 11
        };
        int value = 3;
        int expectedIndex = 0;
        assertThat(SearchIndex.indexOf(array, value), is(expectedIndex));
    }

    @Test
    public void whenBinarySearchIsNotFound() {
        int[] array = new int[] {
                14, 46, 32, 67, 3, 11, 77, 87, 44, 95, 355, 22, 15, 67, 433, 11
        };
        int value = 100;
        int expectedIndex = -1;
        assertThat(SearchIndex.indexOf(array, value), is(expectedIndex));
    }

}