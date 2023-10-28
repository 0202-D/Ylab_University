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
/**
 * Dto ответа по истории транзакции
 */
public class TransactionHistoryRsDto {
    /**
     * идентификатор транзакции
     */
    private Long transactionId;
    /**
     * тип транзакции
     */
    private TransactionalType transactionalType;
    /**
     * сумма транзакции
     */
    private BigDecimal sum;
    /**
     * идентификатор пользователя
     */
    private long userId;
}
