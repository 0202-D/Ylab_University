package io.ylab.dao.transaction;

import io.ylab.model.Transaction;

import java.util.List;
import java.util.Optional;


public interface TransactionRepository {
    void addTransaction(Transaction transaction);

    List<Transaction> getAllByUserName(String userName);

    Optional<Transaction> getById(Long transactionId);
}

