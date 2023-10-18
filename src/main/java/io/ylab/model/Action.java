package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
/**
 * Класс, описывающий действие пользователя в системе.
 */
public class Action {
    /**
     * идентификатор действия
     */
    long actionId;
    /**
     * Объект пользователя, выполнившего действие.
     */
    User user;
    /**
     * Тип действия пользователя.
     */
    Activity activity;
}
