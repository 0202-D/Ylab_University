package io.ylab.controller;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.CreditAndDebitRqDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance/{userId}")
    public UserBalanceRsDto balance(@PathVariable("userId") long userid) {
        return userService.balance(userid);
    }

    @PostMapping("/debit")
    public ResponseEntity debit(@RequestBody CreditAndDebitRqDto debitRq) {
        userService.debit(debitRq.getSum(), debitRq.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/credit}")
    public ResponseEntity credit(@RequestBody CreditAndDebitRqDto creditAndDebitRqDto) {
        userService.credit(creditAndDebitRqDto.getSum(), creditAndDebitRqDto.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
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
