package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Создали потокобезопасный код программы для хранения банковских счетов,
 * в котором можно переводить деньги с одного счёта на другой параллельно.
 * Использовались аннотации jcip, инструмент синхронизации - synchronized.
 */

@ThreadSafe
public final class AccountStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        if (from.isPresent()
                && to.isPresent()
                && from.get().amount() >= amount
                && !from.equals(to)) {
            Account fromAcc = from.get();
            Account toAcc = to.get();
            update(new Account(fromAcc.id(), fromAcc.amount() - amount));
            update(new Account(toAcc.id(), toAcc.amount() + amount));
            result = true;
        }
        return result;
    }
}
