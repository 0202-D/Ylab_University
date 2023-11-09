package io.ylab.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация по операциям")
/**
 * Dto запроса по проведению транзакции
 */
public class CreditAndDebitRqDto {
    /**
     * сумма пополнения
     */
    @NotBlank
    @Schema(name = "sum",description = "Сумма транзакции")
    private BigDecimal sum;
    /**
     * идентификатор пользователя
     */
    @NotBlank
    @Schema(name = "userId",description = "Идентификатор пользователя")
    private long userId;
}
