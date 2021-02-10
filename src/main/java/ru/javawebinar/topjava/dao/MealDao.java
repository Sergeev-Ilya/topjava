package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;

public class MealDao implements Dao<Meal> {
    private static final AtomicInteger count = new AtomicInteger();
    private static final List<Meal> meals = new ArrayList<>();

    static {
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(count.incrementAndGet(), of(2020, JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(final int id) {
        return meals.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void update(final Meal meal) {
        final Meal mealToUpdate = getById(meal.getId());
        setAllPropertiesFromTo(mealToUpdate, meal);
    }

    @Override
    public void delete(final int id) {
        meals.remove(getById(id));
    }

    @Override
    public void add(final Meal meal) {
        meals.add(new Meal(count.incrementAndGet(), meal));
    }

    private void setAllPropertiesFromTo(final Meal mealToUpdate, final Meal updatedMeal) {
        mealToUpdate.setCalories(updatedMeal.getCalories());
        mealToUpdate.setDateTime(updatedMeal.getDateTime());
        mealToUpdate.setDescription(updatedMeal.getDescription());
    }
}
