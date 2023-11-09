package io.ylab.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Dto ответа по проведению транзакции
 */
@Schema(description = "Информация по статусу ответа по проведенной операции")
public class CreditAndDebitRsDto {
    /**
     * код ответа
     */
    @Schema(name = "httpResponse",description = "Код http ответа")
    private int httpResponse;
}
