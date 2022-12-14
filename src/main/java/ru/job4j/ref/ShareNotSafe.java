package ru.job4j.ref;

import java.util.List;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                    List<User> buf = cache.findAll();
                    buf.get(0).setName("test");
                }
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
        System.out.println(cache.findAll());
    }
}
