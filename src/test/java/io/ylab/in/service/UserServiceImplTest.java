package io.ylab.in.service;

import io.ylab.Utils;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.model.*;
import io.ylab.service.UserServiceImpl;
import io.ylab.utils.ConsoleWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ActionRepository actionRepository;

    @Mock
    ConsoleWriter consoleWriter;

    @InjectMocks
    UserServiceImpl userService;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testDebitMethodSuccess() {
        User user = Utils.getUser();
        Transaction transaction = new Transaction(1L, TransactionalType.DEBIT, new BigDecimal("50"), user);
        boolean result = userService.debit(new BigDecimal("50"), user, transaction);
        assertEquals(new BigDecimal("50"), user.getBalance());
        Mockito.verify(transactionRepository).addTransaction(transaction);
        Mockito.verify(actionRepository).addAction(new Action(user, Activity.DEBIT));
        assertTrue(result);
    }

    @Test
    void testCreditMethodSuccess() {
        User user = Utils.getUser();
        Transaction transaction = new Transaction(1L, TransactionalType.CREDIT, new BigDecimal("50"), user);
        boolean result = userService.credit(new BigDecimal("50"), user, transaction);
        assertEquals(new BigDecimal("150"), user.getBalance());
        Mockito.verify(transactionRepository).addTransaction(transaction);
        Mockito.verify(actionRepository).addAction(new Action(user, Activity.CREDIT));
        assertTrue(result);
    }

    @Test
    void testHistoryMethod() {
        User user = Utils.getUser();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, TransactionalType.DEBIT, new BigDecimal("50"), user));
        transactions.add(new Transaction(2L, TransactionalType.CREDIT, new BigDecimal("100"), user));
        Mockito.when(transactionRepository.getAllByUserName(user.getUserName())).thenReturn(transactions);
        List<Transaction> result = userService.history(user);
        Mockito.verify(transactionRepository).getAllByUserName(user.getUserName());
        Mockito.verify(actionRepository).addAction(new Action(user, Activity.HISTORY));
        assertEquals(transactions, result);
    }

    @Test
    void testActivityMethod() {
        User user = Utils.getUser();
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(user, Activity.ENTERED));
        actions.add(new Action(user, Activity.EXITED));
        Mockito.when(actionRepository.getAllByUserName(user.getUserName())).thenReturn(actions);
        List<Action> result = userService.activity(user);
        Mockito.verify(actionRepository).getAllByUserName(user.getUserName());
        assertEquals(actions, result);
    }

    @Test
    void testDebitMethodInsufficientBalance() {
        User user = Utils.getUser();
        Transaction transaction = new Transaction(1L, TransactionalType.DEBIT, new BigDecimal("100"), user);
        boolean result = userService.debit(new BigDecimal("200"), user, transaction);
        Mockito.verify(consoleWriter).print("Средств недостаточно");
        Mockito.verify(actionRepository, Mockito.never()).addAction(Mockito.any(Action.class));
        Mockito.verify(transactionRepository, Mockito.never()).addTransaction(Mockito.any(Transaction.class));
        assertEquals(new BigDecimal("100"), user.getBalance());
        assertFalse(result);
    }
}
