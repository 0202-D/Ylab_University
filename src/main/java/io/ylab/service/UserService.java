package io.ylab.service;

import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;
import io.ylab.model.Action;
import io.ylab.model.Transaction;
import io.ylab.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserBalanceRs balance(long userId);

    boolean debit(BigDecimal sum, User user, Transaction transaction);

    boolean credit(BigDecimal sum, User user, Transaction transaction);

    List<TransactionHistoryDtoRs> history(long userId);

    List<Action> activity(User currentUser);
}
