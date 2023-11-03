package io.ylab.mapper.transaction;

import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public TransactionHistoryRsDto toDtoRs(Transaction transaction) {
        return TransactionHistoryRsDto.builder()
                .transactionId(transaction.getTransactionId())
                .sum(transaction.getSum())
                .transactionalType(transaction.getTransactionalType())
                .userId(transaction.getUser().getUserId())
                .build();
    }
}
