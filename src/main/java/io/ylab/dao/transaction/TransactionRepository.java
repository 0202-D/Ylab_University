package io.ylab.dao.transaction;

import io.ylab.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> transactions = new ArrayList<>();

    void addTransaction(Transaction transaction);
}

