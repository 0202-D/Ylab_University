package io.ylab.service;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserBalanceRsDto balance(long userId);

    boolean debit(BigDecimal sum, long userId);

    boolean credit(BigDecimal sum, long userId);

    List<TransactionHistoryRsDto> history(long userId);

    List<ActivityRsDto> activity(long userId);
}
