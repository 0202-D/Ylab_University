package io.ylab.controller;

import com.google.gson.Gson;
import io.ylab.dto.activity.ActivityRs;
import io.ylab.dto.transaction.CreditRq;
import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;
import io.ylab.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.Reader;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    public static final String APPLICATION_JSON = "application/json";
    private final UserService userService;

    public UserBalanceRs balance(long userid) {
        return userService.balance(userid);
    }

    public boolean debit(Reader body, HttpServletResponse resp) {
        resp.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var creditRq = gson.fromJson(body, CreditRq.class);
        return userService.debit(creditRq.getSum(), creditRq.getUserId());
    }

    public boolean credit(Reader body, HttpServletResponse resp) {
        resp.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var creditRq = gson.fromJson(body, CreditRq.class);
        return userService.credit(creditRq.getSum(), creditRq.getUserId());
    }

    public List<TransactionHistoryDtoRs> history(long userId) {
        return userService.history(userId);
    }

    public List<ActivityRs> activity(Long userId) {
        return userService.activity(userId);
    }
}
