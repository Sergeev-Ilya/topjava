package ru.javawebinar.topjava.repository;

import java.util.List;

public interface MealRepository<T> {
    List<T> getAll();

    T getById(Integer id);

    void delete(Integer id);

    void save(T t);
}
