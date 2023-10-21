package io.ylab.dto.transaction;

import io.ylab.model.TransactionalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryDtoRs {
    private Long transactionId;

    private TransactionalType transactionalType;

    private BigDecimal sum;

    private long userId;
}
