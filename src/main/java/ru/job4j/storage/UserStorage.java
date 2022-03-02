package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;


@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean result = false;
        if (!users.contains(user)) {
            users.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (findById(user.getId()) != null) {
            users.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = findById(fromId);
        User userTo = findById(toId);
        if (userFrom != null && userTo != null
                && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            update(userFrom);
            update(userTo);
            result = true;
        }
        return result;
    }

    public User findById(int id) {
       return users.get(id);
    }
}
