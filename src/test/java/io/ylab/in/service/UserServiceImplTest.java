package io.ylab.in.service;

import io.ylab.dao.TransactionRepository;
import io.ylab.dao.UserRepository;
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
    private TransactionRepository transactionRepository;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(transactionRepository, userRepository);
        userRepository.actions = new ArrayList<>();
        System.setOut(new PrintStream(outputStreamCaptor));
        transactionRepository.transactions = new ArrayList<>();
    }

    @Test
    void testBalance() {
        User user = new User("Иван", "123", new BigDecimal(100)); // Создаем тестового пользователя
        userService.balance(user); // Вызываем метод balance
        assertEquals("Ваш баланс = 100\n**********************\n".replaceAll("\\r|\\n", ""),
                outputStreamCaptor.toString().replaceAll("\\r|\\n", ""));
        verify(userRepository).actions.add(new Action(user, Activity.BALANCE)); // Проверяем, что в UserRepository была добавлена соответствующая активность
    }

    @Test
    void testActivity() {
        User user = new User("Иван", "123", new BigDecimal("100.00"));
        Transaction transaction = new Transaction();
        userRepository.actions.add(new Action(user, Activity.DEBIT));
        userRepository.actions.add(new Action(user, Activity.CREDIT));
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
        assertFalse(transactionRepository.transactions.contains(transaction));
        assertFalse(userRepository.actions.contains(new Action(user, Activity.DEBIT)));
    }
    @Test
    void testCredit() {
        User user = new User("Иван", "123", new BigDecimal("100.00"));
        Transaction transaction = new Transaction();
        BigDecimal sum = new BigDecimal("50.00");

        assertTrue(userService.credit(sum, user, transaction));
        assertEquals(new BigDecimal("150.00"), user.getBalance());
        assertTrue(transactionRepository.transactions.contains(transaction));
        assertTrue(userRepository.actions.contains(new Action(user, Activity.CREDIT)));
    }
    @Test
    void testHistory() {
        User user = new User("Bob","123" ,new BigDecimal("100.00"));
        Transaction transaction1 = new Transaction(new AtomicLong(1),
                TransactionalType.DEBIT,new BigDecimal(100),user);
        Transaction transaction2 = new Transaction(new AtomicLong(1),
                TransactionalType.DEBIT,new BigDecimal(100),user);
        transactionRepository.transactions.add(transaction1);
        transactionRepository.transactions.add(transaction2);
        Transaction unrelatedTransaction = new Transaction(new AtomicLong(1),
                TransactionalType.DEBIT,new BigDecimal(100),user);

        assertEquals(2, userService.history(user).size());
        assertTrue(userService.history(user).contains(transaction1));
        assertTrue(userService.history(user).contains(transaction2));
        assertFalse(userService.history(user).contains(unrelatedTransaction));
        assertTrue(userRepository.actions.contains(new Action(user, Activity.HISTORY)));
    }


}
