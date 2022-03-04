package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        AtomicBoolean result = new AtomicBoolean(false);
        memory.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() == model.getVersion()) {
                value.setVersion(value.getVersion() + 1);
                result.set(true);
                return model;
            }
            throw new OptimisticException("Versions are not equal");
        });
        return result.get();
    }

    public void delete(Base model) {
         memory.remove(model.getId(), model);
    }

    public Base get(int id) {
        return memory.get(id);
    }

}