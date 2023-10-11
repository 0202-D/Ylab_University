package io.ylab.dao.user;

import io.ylab.model.User;

import java.util.Optional;

public interface UserRepository {
    void addUser(User user);

    Optional<User> getByName(String name);
}
