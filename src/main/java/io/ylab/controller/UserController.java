package io.ylab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.CreditAndDebitRqDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Пользовательские операции")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "Просмотр баланса")
    @GetMapping("/balance/{userId}")
    public UserBalanceRsDto balance(@PathVariable("userId") long userid) {
        return userService.balance(userid);
    }
    @Operation(summary = "Снятие средств со счета")
    @PostMapping("/debit")
    public ResponseEntity debit(@RequestBody CreditAndDebitRqDto debitRq) {
        userService.debit(debitRq.getSum(), debitRq.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Пополнение средств")
    @PostMapping("/credit")
    public ResponseEntity credit(@RequestBody CreditAndDebitRqDto creditAndDebitRqDto) {
        userService.credit(creditAndDebitRqDto.getSum(), creditAndDebitRqDto.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Просмотр истории")
    @GetMapping("/history/{userId}")
    public List<TransactionHistoryRsDto> history(@PathVariable("userId") long userId) {
        return userService.history(userId);
    }
    @Operation(summary = "Просмотр активности пользователя")
    @GetMapping("/activity/{userId}")
    public List<ActivityRsDto> activity(@PathVariable("userId") Long userId) {
        return userService.activity(userId);
    }
}
