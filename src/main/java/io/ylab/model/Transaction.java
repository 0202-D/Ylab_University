package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * Класс, представляющий транзакцию.
 */
public class Transaction {
    /**
     * Уникальный идентификатор транзакции.
     */
    AtomicLong id;
    /**
     * Тип транзакции (debit, credit).
     */
    TransactionalType transactionalType;
    /**
     * Сумма транзакции.
     */
    BigDecimal sum;
    /**
     * Пользователь, выполнивший транзакцию.
     */
    User user;
}
