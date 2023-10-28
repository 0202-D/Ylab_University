package io.ylab.dto.transaction;

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
public class CreditAndDebitRsDto {
    /**
     * код ответа
     */
    private int httpResponse;
}
