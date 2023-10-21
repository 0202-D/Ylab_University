package io.ylab.controller;

import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;
import io.ylab.service.UserService;
import io.ylab.model.Action;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public UserBalanceRs balance(long userid) {
        return userService.balance(userid);
    }

    public boolean debit(BigDecimal sum, User user, Transaction transaction) {
        return userService.debit(sum, user, transaction);
    }

    public boolean credit(BigDecimal sum, User user, Transaction transaction) {
        return userService.credit(sum, user, transaction);
    }

    public List<TransactionHistoryDtoRs> history(long userId) {
        return userService.history(userId);
    }

    public List<Action> activity(User currentUser) {
        return userService.activity(currentUser);
    }
}
