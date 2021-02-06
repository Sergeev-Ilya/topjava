package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> filteredByCycles(final List<Meal> meals, final LocalTime startTime,
                                                final LocalTime endTime, final int caloriesPerDay) {
        final Map<LocalDate, Integer> calories = new HashMap<>();
        for (Meal meal : meals) {
            calories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        final List<MealTo> mealsTo = new ArrayList<>();
        for (Meal meal : meals) {
            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsTo.add(createTo(meal, calories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        mealsTo.sort(comparing(MealTo::getDateTime).reversed());
        return mealsTo;
    }

    public static List<MealTo> filteredByStreams(final List<Meal> meals, final LocalTime startTime,
                                                 final LocalTime endTime, final int caloriesPerDay) {
        final Map<LocalDate, Integer> calories = meals.stream()
                .collect(groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        summingInt(Meal::getCalories)));

        return meals.stream()
                .sorted(comparing(Meal::getDateTime).reversed())
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> createTo(meal, calories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
