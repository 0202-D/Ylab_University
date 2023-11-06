package io.ylab.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Информация по балансу пользователя")
/**
 * dto ответа на запрос по балансу
 */
public class UserBalanceRsDto {
   /**
    * имя пользователя
    */
   @Schema(name = "userName",description = "Имя пользователя")
   private String userName;
   /**
    * баланс
    */
   @Schema(name = "balance",description = "Сумма")
   private BigDecimal balance;

}
