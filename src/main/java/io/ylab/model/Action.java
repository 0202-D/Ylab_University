package io.ylab.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
/**
 * Класс, описывающий действие пользователя в системе.
 */
public class Action {
    /**
     * Объект пользователя, выполнившего действие.
     */
    User user;
    /**
     * Тип действия пользователя.
     */
    Activity activity;
}
