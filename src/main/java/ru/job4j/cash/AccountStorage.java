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
        Account fromAc = accounts.get(fromId);
        Account toAc = accounts.get(toId);
        if (accounts.containsKey(fromId)
                && accounts.containsKey(toId)
                && fromAc != null
                && toAc != null) {
            if (fromAc.amount() >= amount) {
                update(new Account(fromId, fromAc.amount() - amount));
                update(new Account(toId, toAc.amount() + amount));
                rsl = true;
            }
        }
        return rsl;
    }
}
