package io.ylab.utils;

import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.model.User;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionGenerator {
    private static AtomicLong id = new AtomicLong(0);

    public Transaction generateTransaction(TransactionalType transactionalType, BigDecimal sum, User user) {
        return Transaction.builder()
                .transactionId(id.incrementAndGet())
                .transactionalType(transactionalType)
                .sum(sum)
                .user(user)
                .build();
    }
}
