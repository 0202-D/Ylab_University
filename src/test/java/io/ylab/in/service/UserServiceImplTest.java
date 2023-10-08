package io.ylab.in.service;

import io.ylab.dao.transaction.TransactionInMemoryRepository;
import io.ylab.dao.action.ActionInMemoryRepository;
import io.ylab.dao.user.UserInMemoryRepository;
import io.ylab.model.*;
import io.ylab.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(transactionInMemoryRepository,actionInMemoryRepository);
        actionInMemoryRepository.actions = new ArrayList<>();
        System.setOut(new PrintStream(outputStreamCaptor));
        transactionInMemoryRepository.transactions = new ArrayList<>();
    }

    @Test
    void testBalance() {
        User user = new User("Иван", "123", new BigDecimal(100)); // Создаем тестового пользователя
        userService.balance(user); // Вызываем метод balance
        assertEquals("Ваш баланс = 100\n**********************\n".replaceAll("\\r|\\n", ""),
                outputStreamCaptor.toString().replaceAll("\\r|\\n", ""));
        verify(actionInMemoryRepository).actions.add(new Action(user, Activity.BALANCE)); // Проверяем, что в UserInMemoryRepository была добавлена соответствующая активность
    }

    @Test
    void testActivity() {
        User user = new User("Иван", "123", new BigDecimal("100.00"));
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
    void testDebitBalance() {
        User user = new User("Иван","123", new BigDecimal("100.00"));
        Transaction transaction = new Transaction();
        BigDecimal sum = new BigDecimal("150.00");

        assertFalse(userService.debit(sum, user, transaction));
        assertEquals(new BigDecimal("100.00"), user.getBalance());
        assertFalse(transactionInMemoryRepository.transactions.contains(transaction));
        assertFalse(actionInMemoryRepository.actions.contains(new Action(user, Activity.DEBIT)));
    }
    @Test
    void testCredit() {
        User user = new User("Иван", "123", new BigDecimal("100.00"));
        Transaction transaction = new Transaction();
        BigDecimal sum = new BigDecimal("50.00");

        assertTrue(userService.credit(sum, user, transaction));
        assertEquals(new BigDecimal("150.00"), user.getBalance());
        assertTrue(transactionInMemoryRepository.transactions.contains(transaction));
        assertTrue(actionInMemoryRepository.actions.contains(new Action(user, Activity.CREDIT)));
    }
    @Test
    void testHistory() {
        User user = new User("Bob","123" ,new BigDecimal("100.00"));
        Transaction transaction1 = new Transaction(new AtomicLong(1),
                TransactionalType.DEBIT,new BigDecimal(100),user);
        Transaction transaction2 = new Transaction(new AtomicLong(1),
                TransactionalType.DEBIT,new BigDecimal(100),user);
        transactionInMemoryRepository.transactions.add(transaction1);
        transactionInMemoryRepository.transactions.add(transaction2);
        Transaction unrelatedTransaction = new Transaction(new AtomicLong(1),
                TransactionalType.DEBIT,new BigDecimal(100),user);

        assertEquals(2, userService.history(user).size());
        assertTrue(userService.history(user).contains(transaction1));
        assertTrue(userService.history(user).contains(transaction2));
        assertFalse(userService.history(user).contains(unrelatedTransaction));
        assertTrue(actionInMemoryRepository.actions.contains(new Action(user, Activity.HISTORY)));
    }


}
