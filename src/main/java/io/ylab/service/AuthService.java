package io.ylab.service;

import io.ylab.dto.user.UserRqDto;
import io.ylab.model.User;

public interface AuthService {

    User addUser(UserRqDto user);

    User authenticateUser(UserRqDto user);
}
