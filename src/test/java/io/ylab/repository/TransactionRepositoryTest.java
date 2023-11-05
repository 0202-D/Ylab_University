package io.ylab.repository;

import io.ylab.Utils;
import io.ylab.dao.transaction.JdbcTransactionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionRepositoryTest {
    @Autowired
    private JdbcTransactionRepository transactionRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Транзакция должна быть успешно добавлена")
    void testAddTransaction() {
        Transaction transaction = Utils.getCreditTransaction();
        User user = Utils.getUser();
        transaction.setUser(user);
        transactionRepository.addTransaction(transaction);
        when(userRepository.getById(anyLong())).thenReturn(Optional.ofNullable(user));
        Optional<Transaction> savedTransaction = transactionRepository.getById(transaction.getTransactionId());
        Assertions.assertTrue(savedTransaction.isPresent());
        Assertions.assertEquals(transaction.getTransactionalType(), savedTransaction.get().getTransactionalType());
        Assertions.assertEquals(transaction.getSum(), savedTransaction.get().getSum());
        Assertions.assertEquals(transaction.getUser().getUserId(), savedTransaction.get().getUser().getUserId());
    }

    @Test
    @DisplayName("Должны быть корректно найдены все транзакции по имени пользователя")
    void testGetAllByUserName() {
        User user = Utils.getUser();
        String userName = user.getUserName();
        when(userRepository.getByName(any())).thenReturn(Optional.ofNullable(user));
        List<Transaction> transactions = transactionRepository.getAllByUserName(userName);
        Assertions.assertFalse(transactions.isEmpty());
        for (Transaction transaction : transactions) {
            Assertions.assertEquals(userName, transaction.getUser().getUserName());
        }
    }

    @Test
    @DisplayName("Должны быть корректно найдены все транзакции по идентификатору")
    void testGetById() {
        Long transactionId = 1L;
        User user = Utils.getUser();
        when(userRepository.getById(anyLong())).thenReturn(Optional.ofNullable(user));
        Optional<Transaction> transaction = transactionRepository.getById(transactionId);
        Assertions.assertTrue(transaction.isPresent());
        Assertions.assertEquals(transactionId, transaction.get().getTransactionId());
    }
}


