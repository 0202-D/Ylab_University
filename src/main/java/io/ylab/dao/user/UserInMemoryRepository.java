package io.ylab.dao.user;

import io.ylab.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInMemoryRepository implements UserRepository {

    public final List<User> users = new ArrayList<>();

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> getByName(String userName) {
        return users.stream().filter(e -> e.getUserName().equals(userName)).findFirst();
    }

    @Override
    public Optional<User> getById(Long id) {
        return users.stream().filter(e -> e.getUserId() == id).findFirst();
    }

    @Override
    public void updateBalance(long userId, BigDecimal bigDecimal) {
        User user = users.stream().filter(u -> u.getUserId() == userId)
                .findFirst().orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
        user.setBalance(bigDecimal);
    }
}
