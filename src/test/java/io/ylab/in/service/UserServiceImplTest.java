package io.ylab.in.service;

import io.ylab.Utils;
import io.ylab.dao.action.ActionInMemoryRepository;
import io.ylab.dao.transaction.TransactionInMemoryRepository;
import io.ylab.dao.user.UserInMemoryRepository;
import io.ylab.model.*;
import io.ylab.service.UserServiceImpl;
import io.ylab.utils.ConsoleWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {
    private UserServiceImpl userService;

    @Mock
    private TransactionInMemoryRepository transactionInMemoryRepository;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private UserInMemoryRepository userInMemoryRepository;

    @Mock
    private  ActionInMemoryRepository actionInMemoryRepository;

    ConsoleWriter consoleWriter = new ConsoleWriter();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(transactionInMemoryRepository,actionInMemoryRepository,consoleWriter);
        actionInMemoryRepository.actions = new ArrayList<>();
        System.setOut(new PrintStream(outputStreamCaptor));
        transactionInMemoryRepository.transactions = new ArrayList<>();
    }

    @Test
    @DisplayName("Проверка метода вывода баланса")
    void testBalance() {
        User user = Utils.getUser();
        userService.balance(user);
        assertEquals("Ваш баланс = 100\n**********************\n".replaceAll("\\r|\\n", ""),
                outputStreamCaptor.toString().replaceAll("\\r|\\n", ""));
        verify(actionInMemoryRepository).actions.add(new Action(user, Activity.BALANCE));
    }

    @Test
    @DisplayName("Проверка метода вывода активности")
    void testActivity() {
        User user = Utils.getUser();
        Transaction transaction = new Transaction();
        actionInMemoryRepository.actions.add(new Action(user, Activity.DEBIT));
        actionInMemoryRepository.actions.add(new Action(user, Activity.CREDIT));
        Action unrelatedAction = new Action(new User(), Activity.DEBIT);

        assertEquals(2, userService.activity(user).size());
        assertTrue(userService.activity(user).contains(new Action(user, Activity.DEBIT)));
        assertTrue(userService.activity(user).contains(new Action(user, Activity.CREDIT)));
        assertFalse(userService.activity(user).contains(unrelatedAction));
    }

    @Test
    @DisplayName("Проверка метода снятия средств")
    void testDebitBalance() {
        User user = Utils.getUser();
        Transaction transaction = new Transaction();
        BigDecimal sum = new BigDecimal("150");

        assertFalse(userService.debit(sum, user, transaction));
        assertEquals(new BigDecimal("100"), user.getBalance());
        assertFalse(transactionInMemoryRepository.transactions.contains(transaction));
        assertFalse(actionInMemoryRepository.actions.contains(new Action(user, Activity.DEBIT)));
    }
    @Test
    @DisplayName("Проверка метода пополнения баланса")
    void testCredit() {
        User user = Utils.getUser();
        Transaction transaction = new Transaction();
        BigDecimal sum = new BigDecimal("50");

        assertTrue(userService.credit(sum, user, transaction));
        assertEquals(new BigDecimal("150"), user.getBalance());
        assertTrue(transactionInMemoryRepository.transactions.contains(transaction));
        assertTrue(actionInMemoryRepository.actions.contains(new Action(user, Activity.CREDIT)));
    }

}
