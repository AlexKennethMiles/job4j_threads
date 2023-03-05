package ru.job4j.ref;

/**
 * В данном классе можно убедиться:
 * Thread-0 и Thread-1 пытаются поменять
 * имя через {@link User#setName(String)} и через {@link UserCache#findAll()},
 * но у них не получится поменять сущность внутри объекта класса {@link UserCache},
 * так как он защищён через объекты-обёртки.
 */
public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("main");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("first");
                        cache.findAll().get(0).setName("first");
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("second");
                        cache.findAll().get(0).setName("second");
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(user.getName());
        System.out.println(cache.findById(1).getName());
    }
}