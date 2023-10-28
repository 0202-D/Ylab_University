package io.ylab.mapper.transaction;

import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.model.Transaction;

public interface TransactionMapper {
    TransactionHistoryRsDto toDtoRs(Transaction transaction);

}
