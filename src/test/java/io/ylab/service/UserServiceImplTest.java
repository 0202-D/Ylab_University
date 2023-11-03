package io.ylab.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
   /* @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ActionRepository actionRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("Тест метода получения баланса")
    void testBalanceValidUserId() {
        User user = Utils.getUser();
        long userId = user.getUserId();
        when(userRepository.getById(userId)).thenReturn(Optional.of(user));
        UserBalanceRsDto result = userService.balance(userId);
        assertNotNull(result);
        assertEquals("username", result.getUserName());
        assertEquals(BigDecimal.valueOf(100), result.getBalance());
    }

    @Test
    @DisplayName("Тест снятия средств")
    void testDebitValidSumAndUserIdWithSufficientBalance() {
        BigDecimal sum = BigDecimal.valueOf(50);
        User user = Utils.getUser();
        long userId = user.getUserId();
        when(userRepository.getById(userId)).thenReturn(Optional.of(user));
        userService.debit(sum, userId);
        assertEquals(BigDecimal.valueOf(50), user.getBalance());
        verify(transactionRepository, times(1)).addTransaction(any(Transaction.class));
        verify(actionRepository, times(1)).addAction(any(Action.class));
    }

    @Test
    @DisplayName("Тест метода пополнения счета")
    void testCreditValidSumAndUserId() {
        User user = Utils.getUser();
        long userId = user.getUserId();
        BigDecimal sum = BigDecimal.valueOf(50);
        when(userRepository.getById(userId)).thenReturn(Optional.of(user));
        userService.credit(sum, userId);
        assertEquals(BigDecimal.valueOf(150), user.getBalance());
        verify(transactionRepository, times(1)).addTransaction(any(Transaction.class));
        verify(actionRepository, times(1)).addAction(any(Action.class));
    }

    @Test
    @DisplayName("Тест метода получения истории")
    void testHistoryValidUserId() {
        User user = Utils.getUser();
        long userId = user.getUserId();
        List<Transaction> transactions = Arrays.asList(
                Utils.getCreditTransaction(),
                Utils.getDebitTransaction()
        );
        when(userRepository.getById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.getAllByUserName("username")).thenReturn(transactions);
        List<TransactionHistoryRsDto> result = userService.history(userId);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(BigDecimal.valueOf(50), result.get(0).getSum());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(TransactionalType.DEBIT, result.get(1).getTransactionalType());
        assertEquals(userId, result.get(1).getUserId());
    }

    @Test
    @DisplayName("Тест получения активностей")
    void testActivityValidUserId() {
        User user = Utils.getUser();
        long userId = user.getUserId();
        List<Action> actions = Arrays.asList(
                Utils.getEnterAction(user),
                Utils.getExitAction(user)
        );
        when(userRepository.getById(userId)).thenReturn(Optional.of(user));
        when(actionRepository.getAllByUserName("username")).thenReturn(actions);
        List<ActivityRsDto> result = userService.activity(userId);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5L, result.get(0).getActionId());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(Activity.ENTERED, result.get(0).getActivity());
        assertEquals(3L, result.get(1).getActionId());
        assertEquals(userId, result.get(1).getUserId());
        assertEquals(Activity.EXITED, result.get(1).getActivity());
    }
*/
}
