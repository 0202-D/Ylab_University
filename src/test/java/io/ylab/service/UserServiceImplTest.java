package io.ylab.service;

import io.ylab.Utils;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.model.Action;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import io.ylab.utils.ConsoleWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ActionRepository actionRepository;
    @Mock
    ConsoleWriter consoleWriter;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("Тест метода снятия со счетв")
    void testDebitMethodSuccess() {
        User user = Utils.getUser();
        Transaction transaction = Utils.getDebitTransaction();
        boolean result = userService.debit(new BigDecimal("50"), user, transaction);
        assertEquals(new BigDecimal("50"), user.getBalance());
        Mockito.verify(transactionRepository).addTransaction(transaction);
        Mockito.verify(actionRepository).addAction(Utils.getDebitAction(user));
        assertTrue(result);
    }

    @Test
    @DisplayName("Тест метода пополнения счетв")
    void testCreditMethodSuccess() {
        User user = Utils.getUser();
        Transaction transaction = Utils.getCreditTransaction();
        boolean result = userService.credit(new BigDecimal("50"), user, transaction);
        assertEquals(new BigDecimal("150"), user.getBalance());
        Mockito.verify(transactionRepository).addTransaction(transaction);
        Mockito.verify(actionRepository).addAction(Utils.getCreditAction(user));
        assertTrue(result);
    }

    @Test
    @DisplayName("Тест вывода истории")
    void testHistoryMethod() {
        User user = Utils.getUser();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(Utils.getDebitTransaction());
        transactions.add(Utils.getCreditTransaction());
        Mockito.when(transactionRepository.getAllByUserName(user.getUserName())).thenReturn(transactions);
        List<TransactionHistoryDtoRs> result = userService.history(user.getUserId());
        Mockito.verify(transactionRepository).getAllByUserName(user.getUserName());
        Mockito.verify(actionRepository).addAction(Utils.getHistoryAction(user));
    }

    @Test
    @DisplayName("Тест вывода активности")
    void testActivityMethod() {
        User user = Utils.getUser();
        List<Action> actions = new ArrayList<>();
        actions.add(Utils.getEnterAction(user));
        actions.add(Utils.getExitAction(user));
        Mockito.when(actionRepository.getAllByUserName(user.getUserName())).thenReturn(actions);
        List<Action> result = userService.activity(user);
        Mockito.verify(actionRepository).getAllByUserName(user.getUserName());
        assertEquals(actions, result);
    }

    @Test
    @DisplayName("Тест на негативный сценарий снятия со счета")
    void testDebitMethodInsufficientBalance() {
        User user = Utils.getUser();
        Transaction transaction = Utils.getDebitTransaction();
        boolean result = userService.debit(new BigDecimal("200"), user, transaction);
        Mockito.verify(consoleWriter).print("Средств недостаточно");
        Mockito.verify(actionRepository, Mockito.never()).addAction(Mockito.any(Action.class));
        Mockito.verify(transactionRepository, Mockito.never()).addTransaction(Mockito.any(Transaction.class));
        assertEquals(new BigDecimal("100"), user.getBalance());
        assertFalse(result);
    }
}
