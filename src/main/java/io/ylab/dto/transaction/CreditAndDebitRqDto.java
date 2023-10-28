package io.ylab.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Dto запроса по проведению транзакции
 */
public class CreditAndDebitRqDto {
    /**
     * сумма пополнения
     */
    @NotNull
    private BigDecimal sum;
    /**
     * идентификатор пользователя
     */
    @NotNull
    private long userId;
}
