package io.ylab.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация по истории транзакций")
/**
 * Dto ответа по истории транзакции
 */
public class TransactionHistoryRsDto {
    /**
     * идентификатор транзакции
     */
    @Schema(name = "transactionId",description = "Идентификатор транзакции")
    private Long transactionId;
    /**
     * тип транзакции
     */
    @Schema(name = "transactionalType",description = "Тип транзакции")
    private TransactionalType transactionalType;
    /**
     * сумма транзакции
     */
    @Schema(name = "sum",description = "Сумма транзакции")
    private BigDecimal sum;
    /**
     * идентификатор пользователя
     */
    @Schema(name = "userId",description = "Идентификатор пользователя")
    private long userId;
}
