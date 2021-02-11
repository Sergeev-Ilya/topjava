package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;

public class InMemoryMealRepository implements MealRepository<Meal> {
    private static final AtomicInteger count = new AtomicInteger();
    private static final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        save(new Meal(of(2020, JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(of(2020, JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(of(2020, JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(of(2020, JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(of(2020, JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(of(2020, JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(of(2020, JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(final Integer id) {
        return meals.get(id);
    }


    @Override
    public void delete(final Integer id) {
        meals.remove(id);
    }

    @Override
    public void save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(count.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
    }
}
