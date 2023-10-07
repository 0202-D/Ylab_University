package io.ylab.in.service;

import io.ylab.model.Transaction;
import io.ylab.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    void balance(User user);

    boolean debit(BigDecimal sum, User user, Transaction transaction);

    boolean credit(BigDecimal sum, User user,Transaction transaction);

    List<Transaction> history(User user);
}
