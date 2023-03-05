package ru.job4j.ref;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Решили проблему потокобезопасности для класса,
 * путём работы с копиями ссылочных объектов,
 * вместо работы с ними напрямую.
 */
@ThreadSafe
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
        return new ArrayList<>(users.values()
                .stream()
                .map(user -> User.of(user.getName()))
                .toList());
    }
}
