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
    String userName;// Имя пользователя
    String password;// Пароль пользователя
    BigDecimal balance;//анс пользователя
}
