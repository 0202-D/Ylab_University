package io.ylab.controller;

import io.ylab.Utils;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

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
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}