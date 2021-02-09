package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    T getById(int id);
    void update(T t);
    void delete(int id);
    void add(T t);
}
