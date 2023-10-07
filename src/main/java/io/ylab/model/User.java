package io.ylab.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
/**
 * Класс, представляющий пользователя системы.
 */
public class User {
    String userName;// Имя пользователя
    String password;// Пароль пользователя
    BigDecimal balance;//анс пользователя
}
