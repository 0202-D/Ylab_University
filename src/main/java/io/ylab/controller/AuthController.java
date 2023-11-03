package io.ylab.controller;

import io.ylab.dto.user.UserDtoRs;
import io.ylab.dto.user.UserRqDto;
import io.ylab.mapper.user.UserMapper;
import io.ylab.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserMapper mapper;
    private final AuthService authService;

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
