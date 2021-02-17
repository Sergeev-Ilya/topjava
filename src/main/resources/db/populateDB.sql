DELETE FROM user_roles;
DELETE FROM user_meals;
DELETE FROM users;
DELETE FROM meals;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories)
VALUES  (TIMESTAMP '2021-02-11 11:30:00', 'Завтрак', 500),
        (TIMESTAMP '2021-02-11 15:00:00', 'Обед', 1000),
        (TIMESTAMP '2021-02-11 19:30:00', 'Ужин', 500),
        (TIMESTAMP '2021-02-12 00:00:00', 'Еда на граничное значение', 100),
        (TIMESTAMP '2021-02-12 11:30:00', 'Завтрак', 1000),
        (TIMESTAMP '2021-02-12 15:00:00', 'Обед', 1000),
        (TIMESTAMP '2021-02-12 19:30:00', 'Ужин', 410);

INSERT INTO user_meals (meal_id, user_id)
VALUES  (100002, 100000),
        (100003, 100000),
        (100004, 100000),
        (100005, 100000),
        (100006, 100000),
        (100007, 100000),
        (100008, 100000);