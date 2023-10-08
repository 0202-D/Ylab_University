package io.ylab.dao;

import io.ylab.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    public  List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }
}
