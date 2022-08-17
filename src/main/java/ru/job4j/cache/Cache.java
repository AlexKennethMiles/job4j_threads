package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (id, oldBase) -> {
            if (model.getVersion() != memory.get(id).getVersion()) {
                throw new OptimisticException("Different version between two base item!");
            }
            return new Base(model.getId(), model.getVersion() + 1, model.getName());
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        Base buff = memory.get(id);
        return new Base(buff.getId(), buff.getVersion(), buff.getName());
    }
}
