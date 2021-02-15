package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public Collection<MealTo> getAll() {
        return getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public Collection<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                         @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return getFilteredTos(service.getBetweenInclusive(startDate, endDate, authUserId()),
                authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal update(final Meal meal, int id) {
        assureIdConsistent(meal, id);
        return service.update(meal, authUserId());
    }

    private boolean isBlank(String string) {
        return string == null || string.isEmpty();
    }

}