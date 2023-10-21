package io.ylab.controller;

import com.google.gson.Gson;
import io.ylab.dto.user.UserDtoRq;
import io.ylab.dto.user.UserDtoRs;
import io.ylab.mapper.user.UserMapper;
import io.ylab.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import javax.servlet.http.HttpServletResponse;
import java.io.Reader;

@RequiredArgsConstructor
public class AuthController {
    public static final String APPLICATION_JSON = "application/json";

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final AuthService authService;


    public UserDtoRs addUser(Reader body, HttpServletResponse resp) {
        resp.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var userDto = gson.fromJson(body, UserDtoRq.class);
        var user = mapper.toEntity(userDto);
        var newUser = authService.addUser(user);
        if (newUser == null) {
            return null;
        }
        return mapper.toDtoRs(newUser);
    }

    public UserDtoRs authenticateUser(Reader body, HttpServletResponse resp) {
        resp.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var userDto = gson.fromJson(body, UserDtoRq.class);
        var user = mapper.toEntity(userDto);
        var newUser = authService.authenticateUser(user);
        if (newUser == null) {
            return null;
        }
        return mapper.toDtoRs(newUser);

    }
}
