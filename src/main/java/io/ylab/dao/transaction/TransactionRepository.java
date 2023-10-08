package io.ylab.dao.transaction;

import io.ylab.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface TransactionRepository {
    void addTransaction(Transaction transaction);
}

