package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
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

    public Collection<MealTo> getAllFiltered(String startTime, String endTime,
                                             String startDate, String endDate) {
        return getFilteredTos(service.getAll(authUserId()), authUserCaloriesPerDay(),
                isBlank(startDate) ? LocalDate.MIN : LocalDate.parse(startDate),
                isBlank(endDate) ? LocalDate.MAX : LocalDate.parse(endDate),
                isBlank(startTime) ? LocalTime.MIN : LocalTime.parse(startTime),
                isBlank(endTime) ? LocalTime.MAX : LocalTime.parse(endTime));
    }

    public Meal update(final Meal meal) {
        return service.update(meal, authUserId());
    }

    private boolean isBlank(String string) {
        return string == null || string.isEmpty();
    }

}