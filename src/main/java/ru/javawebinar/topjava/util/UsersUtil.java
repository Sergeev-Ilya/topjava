package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

public class UsersUtil {
    public static Iterable<User> users = Arrays.asList(
            new User(null, "User1", "email1@gmail.com", "password", Role.USER)
    );
}
