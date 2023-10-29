package io.ylab.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "user")
public class User {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
