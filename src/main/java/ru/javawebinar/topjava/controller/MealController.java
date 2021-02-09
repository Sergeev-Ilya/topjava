package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealController {
    private final MealDao dao = new MealDao();

    public List<MealTo> getAllMeals(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return MealsUtil.getFilteredTo(dao.getAll(), startTime, endTime, caloriesPerDay);
    }

    public void add(Meal meal) {
        dao.add(meal);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public void update(Meal newMeal) {
        dao.update(newMeal);
    }

    public Meal getById(int id) {
        return dao.getById(id);
    }
}
