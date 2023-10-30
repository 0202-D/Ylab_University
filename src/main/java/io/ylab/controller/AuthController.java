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
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/reg")
    public UserDtoRs addUser(@RequestBody @Valid UserRqDto user) {
        var newUser = authService.addUser(user);
        return mapper.toDtoRs(newUser);
    }

    @PostMapping("/auth")
    public UserDtoRs authenticateUser(@RequestBody @Valid UserRqDto user) {
        var newUser = authService.authenticateUser(user);
        return mapper.toDtoRs(newUser);
    }
}
