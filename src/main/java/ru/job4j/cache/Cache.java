package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс имитирует неблокирующий кэш.
 * метод update выкидывает исключение, если версия модели
 * была параллельно изменена другим потоком.
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Атомарный метод вставки в кэш
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }


    /**
     * Метод обновляет данные в кэше.
     * Если версии одной Base model уже отличаются, то выкидывается
     * Optimistic exception
     * @return new Base с обновленной версией.
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base result =  new Base(model.getId(), model.getVersion() + 1);
            result.setName(model.getName());
            return result;
        }) != null;
    }

    public void delete(Base model) {
         memory.remove(model.getId(), model);
    }

    public Base get(int id) {
        return memory.get(id);
    }

}