package io.ylab.controller;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.CreditAndDebitRqDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    public static final String APPLICATION_JSON = "application/json";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public UserBalanceRsDto balance(long userid) {
        return userService.balance(userid);
    }

    public boolean debit(CreditAndDebitRqDto debitRq) {
        return userService.debit(debitRq.getSum(), debitRq.getUserId());
    }

    public boolean credit(CreditAndDebitRqDto creditAndDebitRqDto) {
        return userService.credit(creditAndDebitRqDto.getSum(), creditAndDebitRqDto.getUserId());
    }

    public List<TransactionHistoryRsDto> history(long userId) {
        return userService.history(userId);
    }

    public List<ActivityRsDto> activity(Long userId) {
        return userService.activity(userId);
    }
}
