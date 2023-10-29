package io.ylab.controller;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.CreditAndDebitRqDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    public static final String APPLICATION_JSON = "application/json";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance/{userId}")
    public UserBalanceRsDto balance(@PathVariable("userId") long userid) {
        return userService.balance(userid);
    }

    @PostMapping("/debit")
    public boolean debit(CreditAndDebitRqDto debitRq) {
        return userService.debit(debitRq.getSum(), debitRq.getUserId());
    }

    @PostMapping("/credit}")
    public boolean credit(CreditAndDebitRqDto creditAndDebitRqDto) {
        return userService.credit(creditAndDebitRqDto.getSum(), creditAndDebitRqDto.getUserId());
    }

    @GetMapping("/history/{userId}")
    public List<TransactionHistoryRsDto> history(@PathVariable("userId") long userId) {
        return userService.history(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<ActivityRsDto> activity(@PathVariable("userId") Long userId) {
        return userService.activity(userId);
    }
}
