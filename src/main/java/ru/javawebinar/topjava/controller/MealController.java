package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;

public class MealController {
    private final InMemoryMealRepository repository = new InMemoryMealRepository();

    public List<MealTo> getAllMeals() {
        return MealsUtil.getTos(repository.getAll(), CALORIES_PER_DAY);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public Meal getById(int id) {
        return repository.getById(id);
    }

    public void save(Meal meal) {
        repository.save(meal);
    }
}
