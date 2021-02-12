package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

public class UsersUtil {
    public static Iterable<User> users = Arrays.asList(
            new User(null, "User", "user@gmail.com", "password", Role.USER),
            new User(null, "Admin", "admin@gmail.com", "password", Role.USER, Role.ADMIN)
    );
}
