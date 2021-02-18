package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.TestMatcher.usingFieldsComparator;

public class MealTestData {
    public static TestMatcher<Meal> MEAL_MATCHER = usingFieldsComparator();

    public static final int MEAL_ID = 100002;
    public static final LocalDateTime MEAL_1_DATE_TIME = of(2021, 2, 11, 11, 30);
    public static final Meal MEAL_1 = new Meal(MEAL_ID, MEAL_1_DATE_TIME, "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_ID + 1, of(2021, 2, 11, 15, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_ID + 2, of(2021, 2, 11, 19, 30), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_ID + 3, of(2021, 2, 12, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(MEAL_ID + 4, of(2021, 2, 12, 11, 30), "Завтрак", 1000);
    public static final Meal MEAL_6 = new Meal(MEAL_ID + 5, of(2021, 2, 12, 15, 0), "Обед", 1000);
    public static final Meal MEAL_7 = new Meal(MEAL_ID + 6, of(2021, 2, 12, 19, 30), "Ужин", 410);
    public static final Meal ADMIN_MEAL_1 = new Meal(MEAL_ID + 7, of(2021, 2, 13, 14, 30), "Обед Админа", 1500);
    public static final Meal ADMIN_MEAL_2 = new Meal(MEAL_ID + 8, of(2021, 2, 13, 18, 30), "Ужин Админа", 1510);

    public static final List<Meal> MEALS = Arrays.asList(
            MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);

    public static final int USER_ID = UserTestData.USER_ID;

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2021, 2, 16, 19,30), "test", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("TEST");
        updated.setCalories(1);
        return updated;
    }
}
