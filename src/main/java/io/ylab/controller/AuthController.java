package io.ylab.controller;

import io.ylab.dto.user.UserDtoRs;
import io.ylab.dto.user.UserRqDto;
import io.ylab.mapper.user.UserMapper;
import io.ylab.service.AuthService;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    public static final String APPLICATION_JSON = "application/json";
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public UserDtoRs addUser(@RequestBody @Valid UserRqDto user) {
        var newUser = authService.addUser(user);
        if (newUser == null) {
            return null;
        }
        return mapper.toDtoRs(newUser);
    }

    @PostMapping("/reg")
    public UserDtoRs authenticateUser(@RequestBody @Valid UserRqDto user) {
        var newUser = authService.authenticateUser(user);
        if (newUser == null) {
            return null;
        }
        return mapper.toDtoRs(newUser);
    }
}
