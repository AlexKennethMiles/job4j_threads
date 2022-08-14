package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), new Account(account.id(), account.amount()));
        return checkAccount(account);
    }

    public synchronized boolean update(Account account) {
        accounts.replace(account.id(), new Account(account.id(), account.amount()));
        return checkAccount(account);
    }

    public synchronized boolean delete(int id) {
        accounts.remove(id);
        return accounts.containsKey(id);
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> rsl = Optional.empty();
        if (accounts.containsKey(id)) {
            rsl = Optional.of(new Account(accounts.get(id).id(), accounts.get(id).amount()));
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!accounts.containsKey(fromId)
                || !accounts.containsKey(toId)) {
            return false;
        }
        if (accounts.get(fromId).amount() < amount) {
            return false;
        }
        update(new Account(fromId, accounts.get(fromId).amount() - amount));
        update(new Account(toId, accounts.get(toId).amount() + amount));
        return true;

    }

    private boolean checkAccount(Account account) {
        return accounts.get(account.id()).equals(new Account(account.id(), account.amount()));
    }
}
