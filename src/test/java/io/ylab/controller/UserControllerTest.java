package io.ylab.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  /*  private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Тест метода баланса")
    void testBalance() throws Exception {
        long userId = 1;
        UserBalanceRsDto expectedResponse = Utils.getUserBalanceRsDto();

        Mockito.when(userService.balance(userId)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/balance/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value("100"));
    }

    @Test
    @DisplayName("Тест метода вывода истории")
    void testHistory() throws Exception {
        long userId = 1;
        List<TransactionHistoryRsDto> expectedResponse = Arrays.asList(
                Utils.getTransactionHistoryRsDto()
        );
        Mockito.when(userService.history(userId)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/history/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transactionalType").value(TransactionalType.CREDIT.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sum").value(100));

    }

    @Test
    @DisplayName("Тест метода снятия средств")
    void debitTest() throws Exception {
        CreditAndDebitRqDto request = Utils.getCreditAndDebitRqDto();
        doNothing().when(userService).debit(request.getSum(), request.getUserId());
        mockMvc.perform(post("/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Тест метода пополнения средств")
    void creditTest() throws Exception {
        CreditAndDebitRqDto request = Utils.getCreditAndDebitRqDto();
        doNothing().when(userService).credit(request.getSum(), request.getUserId());
        mockMvc.perform(post("/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testActivity() throws Exception {
        long userId = 1L;
        List<ActivityRsDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(Utils.getActivityRsDto());
        responseDtoList.add(Utils.getSecondActivityRsDto());
        Mockito.when(userService.activity(userId)).thenReturn(responseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/activity/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actionId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].activity").value(Activity.HISTORY.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].actionId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].activity").value( Activity.BALANCE.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(1L));
    }*/
}