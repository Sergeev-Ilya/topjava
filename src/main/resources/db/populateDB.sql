DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES  (TIMESTAMP '2021-02-11 11:30:00', 'Завтрак', 500, 100000),
        (TIMESTAMP '2021-02-11 15:00:00', 'Обед', 1000, 100000),
        (TIMESTAMP '2021-02-11 19:30:00', 'Ужин', 500, 100000),
        (TIMESTAMP '2021-02-12 00:00:00', 'Еда на граничное значение', 100, 100000),
        (TIMESTAMP '2021-02-12 11:30:00', 'Завтрак', 1000, 100000),
        (TIMESTAMP '2021-02-12 15:00:00', 'Обед', 1000, 100000),
        (TIMESTAMP '2021-02-12 19:30:00', 'Ужин', 410, 100000);