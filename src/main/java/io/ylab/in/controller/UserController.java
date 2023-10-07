package io.ylab.in.controller;

import io.ylab.in.dao.UserRepository;
import io.ylab.in.service.UserService;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public void balance(User user) {
        userService.balance(user);
    }

    public boolean debit(BigDecimal sum, User user, Transaction transaction) {
        return userService.debit(sum, user, transaction);
    }

    public boolean credit(BigDecimal sum, User user, Transaction transaction) {
        return userService.credit(sum, user, transaction);
    }

    public List<Transaction> history(User user) {
        return userService.history(user);
    }

}
