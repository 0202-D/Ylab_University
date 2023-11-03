package io.ylab.dto.transaction;

import jakarta.validation.constraints.NotBlank;
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
 * Dto запроса по проведению транзакции
 */
public class CreditAndDebitRqDto {
    /**
     * сумма пополнения
     */
    @NotBlank
    private BigDecimal sum;
    /**
     * идентификатор пользователя
     */
    @NotBlank
    private long userId;
}
