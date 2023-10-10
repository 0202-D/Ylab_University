package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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
    Long transactionId;
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
