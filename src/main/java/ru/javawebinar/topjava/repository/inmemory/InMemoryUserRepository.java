package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final InMemoryBaseRepository<User> users = new InMemoryBaseRepository<>();

    {
        UsersUtil.users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return users.delete(id);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        return users.save(user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return users.getCollection().stream()
                .sorted(comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return users.getCollection().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findAny()
                .orElse(null);
    }
}
