package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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
@Entity
@Table(name = "transaction")
public class Transaction {
    /**
     * Уникальный идентификатор транзакции.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
}
