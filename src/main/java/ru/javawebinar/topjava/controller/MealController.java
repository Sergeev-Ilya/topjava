package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public class MealController {
    private final MealDao dao = new MealDao();

    public List<MealTo> getAllMeals() {
        return dao.getAll();
    }
}
