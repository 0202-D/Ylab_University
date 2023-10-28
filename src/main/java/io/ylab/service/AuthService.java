package io.ylab.service;

import io.ylab.model.User;

public interface AuthService {

    User addUser(User user);

    User authenticateUser(User user);
}
