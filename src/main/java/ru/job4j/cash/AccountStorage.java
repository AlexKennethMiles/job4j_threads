package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount())) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), new Account(account.id(), account.amount())) == null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) == null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (accounts.containsKey(fromId)
                && accounts.containsKey(toId)
                && accounts.get(fromId).amount() >= amount) {
            update(new Account(fromId, accounts.get(fromId).amount() - amount));
            update(new Account(toId, accounts.get(toId).amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}
