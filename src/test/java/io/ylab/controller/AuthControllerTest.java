package io.ylab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.Utils;
import io.ylab.dto.user.UserRqDto;
import io.ylab.model.User;
import io.ylab.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
     void testAuthenticateUser() throws Exception {
       UserRqDto userRqDto = Utils.getUserRqDto();
        User authenticatedUser = Utils.getUser();
        Mockito.when(authService.authenticateUser(userRqDto)).thenReturn(authenticatedUser);
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRqDto)))
                .andExpect(status().isOk());

    }
}