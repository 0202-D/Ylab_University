package io.ylab.dto.user;

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
@Schema(description = "Основная информация по пользователю")
/**
 * dto ответа на регистрацию и аутентификацию пользователя
 */
public class UserDtoRs {
    /**
     * идентификатор пользователя
     */
    @Schema(name = "userId",description = "Идентификатор пользователя")
    private long userId;
    /**
     * имя пользователя
     */
    @Schema(name = "userName",description = "Имя пользователя")
    private  String userName;
    /**
     * баланс пользователя
     */
    private  BigDecimal balance;
}
