package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.Comparator.comparing;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> mapUsers = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        final Map<Integer, Meal> userMeals = mapUsers.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            userMeals.put(meal.getId(), meal);
            mapUsers.putIfAbsent(userId, userMeals);
            return meal;
        }

        return userMeals.isEmpty() ? null : userMeals.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        final Meal meal = get(id, userId);
        return meal != null && mapUsers.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        final Map<Integer, Meal> userMeals = mapUsers.get(userId);
        return userMeals != null ? userMeals.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getAllFiltered(LocalDateTime start, LocalDateTime end, int userId) {
        return filterByPredicate(userId, meal -> isBetweenHalfOpen(meal.getDateTime(), start, end));
    }

    private Collection<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        return mapUsers.getOrDefault(userId, emptyMap())
                .values().stream()
                .filter(filter)
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

