package io.ylab.dao.user;

import io.ylab.model.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository {
    User addUser(User user);

    Optional<User> getByName(String name);

    Optional<User> getById(Long id);

    void updateBalance(long userId, BigDecimal bigDecimal);
}
