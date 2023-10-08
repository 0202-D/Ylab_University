package io.ylab.controller;

import io.ylab.service.AuthService;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public void addUser(){
        authService.addUser();
    }

    public User authenticateUser(){
       return authService.authenticateUser();
    }
}