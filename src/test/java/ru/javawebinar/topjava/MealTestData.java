package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = 100002;
    public static final LocalDateTime MEAL_1_DATE_TIME = of(2021, 2, 11, 11, 30);
    public static final Meal MEAL_1 = new Meal(MEAL_ID, MEAL_1_DATE_TIME, "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_ID + 1, of(2021, 2, 11, 15, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_ID + 2, of(2021, 2, 11, 19, 30), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_ID + 3, of(2021, 2, 12, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(MEAL_ID + 4, of(2021, 2, 12, 11, 30), "Завтрак", 1000);
    public static final Meal MEAL_6 = new Meal(MEAL_ID + 5, of(2021, 2, 12, 15, 0), "Обед", 1000);
    public static final Meal MEAL_7 = new Meal(MEAL_ID + 6, of(2021, 2, 12, 19, 30), "Ужин", 410);

    public static final List<Meal> MEALS = Arrays.asList(
            MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);

    public static final int USER_ID = UserTestData.USER_ID;

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "test", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("TEST");
        updated.setCalories(1);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
