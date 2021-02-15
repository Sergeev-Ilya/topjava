package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, InMemoryBaseRepository<Meal>> mapUsersMeals = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(meal -> save(meal, authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        final InMemoryBaseRepository<Meal> userMeals = mapUsersMeals.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return userMeals.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        final InMemoryBaseRepository<Meal> userMeals = mapUsersMeals.get(userId);
        return userMeals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        final InMemoryBaseRepository<Meal> userMeals = mapUsersMeals.get(userId);
        return userMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getBetweenHalfOpen(LocalDateTime start, LocalDateTime end, int userId) {
        return filterByPredicate(userId, meal -> isBetweenHalfOpen(meal.getDateTime(), start, end));
    }

    private Collection<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        final InMemoryBaseRepository<Meal> userMeals = mapUsersMeals.get(userId);
        return userMeals == null ? Collections.emptyList() :
                userMeals.getCollection().stream()
                .filter(filter)
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

