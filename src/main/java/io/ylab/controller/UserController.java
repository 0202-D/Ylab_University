package io.ylab.controller;

import io.ylab.dto.activity.ActivityRs;
import io.ylab.dto.transaction.CreditAndDebitRq;
import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;
import io.ylab.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserController {
    public static final String APPLICATION_JSON = "application/json";
    private final UserService userService;


    public UserBalanceRs balance(long userid) {
        return userService.balance(userid);
    }

    public boolean debit(CreditAndDebitRq debitRq) {
        return userService.debit(debitRq.getSum(), debitRq.getUserId());
    }

    public boolean credit(CreditAndDebitRq creditAndDebitRq) {
        return userService.credit(creditAndDebitRq.getSum(), creditAndDebitRq.getUserId());
    }

    public List<TransactionHistoryDtoRs> history(long userId) {
        return userService.history(userId);
    }

    public List<ActivityRs> activity(Long userId) {
        return userService.activity(userId);
    }
}
