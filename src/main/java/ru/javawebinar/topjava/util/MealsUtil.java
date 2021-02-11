package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    public static final int CALORIES_PER_DAY = 2000;

    public static List<MealTo> getFilteredTos(final List<Meal> meals, final LocalTime startTime,
                                              final LocalTime endTime, final int caloriesPerDay) {
        final Map<LocalDate, Integer> caloriesSumByDate = groupByDate(meals);

        return meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .sorted(comparing(MealTo::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static List<MealTo> getTos(final List<Meal> meals, final int caloriesPerDay) {
        return getFilteredTos(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    private static Map<LocalDate, Integer> groupByDate(List<Meal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
    }


}
