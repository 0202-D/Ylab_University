package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
/**
 * Класс, описывающий действие пользователя в системе.
 */
@Entity
@Table(name = "action")
public class Action {
    /**
     * идентификатор действия
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long actionId;
    /**
     * Объект пользователя, выполнившего действие.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
    /**
     * Тип действия пользователя.
     */
    Activity activity;
}
