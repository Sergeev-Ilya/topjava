package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(MEAL_ID, USER_ID), MEAL_1);
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
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEALS);
    }

    @Test
    public void update() {
        Meal newMeal = getUpdated();
        service.update(newMeal, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), newMeal);
    }

    @Test
    public void updateNotFound() {
        Meal newMeal = getUpdated();
        newMeal.setId(1000);
        assertThrows(DataAccessException.class, () -> service.update(newMeal, USER_ID));
    }

    @Test
    public void updateWithWrongOwner() {
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal createdMeal = service.create(newMeal, USER_ID);
        Integer newId = createdMeal.getId();
        newMeal.setId(newId);
        assertMatch(createdMeal, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDatetimeCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Meal(MEAL_1_DATE_TIME, "", 1), USER_ID));
    }
}