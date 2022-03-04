package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        assertTrue(cache.add(model1));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        cache.add(model1);
        cache.update(model1);
        Base expected = new Base(1, 1);
        assertEquals(cache.get(1), expected);
    }

    @Test
    public void whenMultiplyAdd() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        cache.add(model1);
        Base model2 = new Base(1, 1);
        assertFalse(cache.add(model2));
    }

    @Test(expected = OptimisticException.class)
    public void whenIsNotUpdated() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.update(model);
        model = new Base(1, 0);
        cache.update(model);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.delete(model);
        assertTrue(cache.add(model));
    }

}