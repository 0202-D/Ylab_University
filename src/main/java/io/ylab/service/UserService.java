package io.ylab.service;

import io.ylab.dto.activity.ActivityRs;
import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserBalanceRs balance(long userId);

    boolean debit(BigDecimal sum, long userId);

    boolean credit(BigDecimal sum, long userId);

    List<TransactionHistoryDtoRs> history(long userId);

    List<ActivityRs> activity(long userId);
}
