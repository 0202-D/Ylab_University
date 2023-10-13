package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Класс, представляющий пользователя системы.
 */
public class User {
    /**
     * Идентификатор пользователя
     */
    long userId;
    /**
     * Имя пользователя
     */
    String userName;
    /**
     * Пароль пользователя
     */
    String password;
    /**
     * Баланс пользователя
     */
    BigDecimal balance;
}
