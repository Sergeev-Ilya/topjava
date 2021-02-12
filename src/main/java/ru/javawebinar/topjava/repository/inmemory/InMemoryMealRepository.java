package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (!meal.hasOwner()) {
            Meal mealDB = get(meal.getId(), authUserId());
            if (mealDB == null) {
                return null;
            }
            meal.setUserId(mealDB.getUserId());
        }

        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        final Meal meal = get(id, userId);
        return meal != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        final Meal meal = repository.get(id);
        return !meal.hasCorrectOwner(userId) ? null : meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.hasCorrectOwner(userId))
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

