package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEAL_1;
import static ru.javawebinar.topjava.MealTestData.MEAL_1_DATE_TIME;
import static ru.javawebinar.topjava.MealTestData.MEAL_2;
import static ru.javawebinar.topjava.MealTestData.MEAL_3;
import static ru.javawebinar.topjava.MealTestData.MEAL_4;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void get() {
        MEAL_MATCHER.assertMatch(service.get(MEAL_ID, USER_ID), MEAL_1);
    }

    @Test
    public void getNotExisted() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID + 10, USER_ID));
    }

    @Test
    public void getWithWrongOwner() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID + 2));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotExisted() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID + 10, USER_ID));
    }

    @Test
    public void deleteWithWrongOwner() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, USER_ID + 2));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate start = LocalDate.of(2021, 2, 1);
        LocalDate end = start.plusDays(10);
        List<Meal> meals = service.getBetweenInclusive(start, end, USER_ID);
        MEAL_MATCHER.assertMatch(meals, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenInclusiveWithWrongOwner() {
        LocalDate start = LocalDate.of(2021, 2, 1);
        LocalDate end = start.plusDays(10);
        List<Meal> meals = service.getBetweenInclusive(start, end, USER_ID + 2);
        MEAL_MATCHER.assertMatch(meals, Collections.emptyList());
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        MEAL_MATCHER.assertMatch(meals, MEALS);
    }

    @Test
    public void update() {
        service.update(getUpdated(), USER_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal newMeal = getUpdated();
        newMeal.setId(1000);
        assertThrows(NotFoundException.class, () -> service.update(newMeal, USER_ID));
    }

    @Test
    public void updateWithWrongOwner() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), USER_ID + 10));
    }

    @Test
    public void create() {
        Meal createdMeal = service.create(getNew(), USER_ID);
        Integer newId = createdMeal.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        newMeal.setDateTime(createdMeal.getDateTime());
        MEAL_MATCHER.assertMatch(createdMeal, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDatetimeCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Meal(MEAL_1_DATE_TIME, "", 1), USER_ID));
    }
}