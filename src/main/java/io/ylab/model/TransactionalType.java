package io.ylab.model;

/**
 * Перечисление, представляющее типы транзакций.
 */
public enum TransactionalType {
    /**
     * Транзакция снятия средств
     */
    DEBIT,
    /**
     * Транзакция пополнения средств
     */
    CREDIT
}
