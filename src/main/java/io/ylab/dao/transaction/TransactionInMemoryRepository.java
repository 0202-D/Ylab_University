package io.ylab.dao.transaction;

import io.ylab.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionInMemoryRepository implements TransactionRepository {
    public final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> getAllByUserName(String userName) {
        return transactions
                .stream().filter(el -> el.getUser().getUserName().equals(userName))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Transaction> getById(Long transactionId) {
        return transactions
                .stream().filter(el -> Objects.equals(el.getTransactionId(), transactionId)).findFirst();
    }
}
