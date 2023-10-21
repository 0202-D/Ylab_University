package io.ylab.mapper.transaction;

import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.model.Transaction;

public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public TransactionHistoryDtoRs toDtoRs(Transaction transaction) {
        return TransactionHistoryDtoRs.builder()
                .transactionId(transaction.getTransactionId())
                .sum(transaction.getSum())
                .transactionalType(transaction.getTransactionalType())
                .userId(transaction.getUser().getUserId())
                .build();
    }
}
