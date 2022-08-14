package ru.job4j.ref;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@NotThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        return new ArrayList<>(deepCopyMap().values());
    }

    private HashMap<Integer, User> deepCopyMap() {
        HashMap<Integer, User> buffMap = new HashMap<>();
        for (Integer integer : users.keySet()) {
            buffMap.put(integer, users.get(integer));
        }
        return buffMap;
    }
}
