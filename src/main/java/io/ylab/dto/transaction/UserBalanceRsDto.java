package io.ylab.dto.transaction;

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
 * dto ответа на запрос по балансу
 */
public class UserBalanceRsDto {
   /**
    * имя пользователя
    */
   private String userName;
   /**
    * баланс
    */
   private BigDecimal balance;

}
