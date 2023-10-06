package io.ylab.in.controller;

import io.ylab.in.dao.UserRepository;
import io.ylab.in.service.UserService;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public void balance(User user){
         userService.balance(user);
    }
}
