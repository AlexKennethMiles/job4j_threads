package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) == null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) == null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> fromAc = Optional.ofNullable(accounts.get(fromId));
        Optional<Account> toAc = Optional.ofNullable(accounts.get(toId));
        if (fromAc.isPresent()
                && toAc.isPresent()
                && fromAc.get().amount() >= amount) {
            update(new Account(fromId, fromAc.get().amount() - amount));
            update(new Account(toId, toAc.get().amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}
