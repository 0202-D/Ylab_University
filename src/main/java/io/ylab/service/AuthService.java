package io.ylab.service;

import io.ylab.model.User;

public interface AuthService {

   boolean addUser();

   User authenticateUser();
}
