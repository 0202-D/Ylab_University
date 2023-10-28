package io.ylab.dto.user;

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
 * dto ответа на регистрацию и аутентификацию пользователя
 */
public class UserDtoRs {
    /**
     * идентификатор пользователя
     */
    private long userId;
    /**
     * имя пользователя
     */
    private  String userName;
    /**
     * баланс пользователя
     */
    private  BigDecimal balance;
}
