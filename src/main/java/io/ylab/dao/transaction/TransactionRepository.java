package io.ylab.dao.transaction;

import io.ylab.model.Transaction;


public interface TransactionRepository {
    void addTransaction(Transaction transaction);
}

