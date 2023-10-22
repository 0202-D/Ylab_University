package io.ylab.controller;

import io.ylab.dto.user.UserDtoRs;
import io.ylab.mapper.user.UserMapper;
import io.ylab.model.User;
import io.ylab.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
public class AuthController {
    public static final String APPLICATION_JSON = "application/json";
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final AuthService authService;

    public UserDtoRs addUser(User user) {
        var newUser = authService.addUser(user);
        if (newUser == null) {
            return null;
        }
        return mapper.toDtoRs(newUser);
    }

    public UserDtoRs authenticateUser(User user) {
        var newUser = authService.authenticateUser(user);
        if (newUser == null) {
            return null;
        }
        return mapper.toDtoRs(newUser);
    }
}
