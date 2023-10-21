package io.ylab.mapper.transaction;

import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.model.Transaction;

public interface TransactionMapper {
    TransactionHistoryDtoRs toDtoRs(Transaction transaction);

}
