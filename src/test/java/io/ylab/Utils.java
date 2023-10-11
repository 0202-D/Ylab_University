package io.ylab;

import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.model.User;

import java.math.BigDecimal;

public class Utils {
    public static User getUser() {
        return User.builder()
                .userName("username")
                .password("password")
                .balance(new BigDecimal(100))
                .build();
    }

    public static Transaction getDebitTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionalType(TransactionalType.DEBIT)
                .sum(new BigDecimal("100"))
                .user(getUser())
                .build();
    }

    public static Transaction getCreditTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionalType(TransactionalType.CREDIT)
                .sum(new BigDecimal("50"))
                .user(getUser())
                .build();
    }

}
