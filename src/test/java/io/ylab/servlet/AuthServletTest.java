package io.ylab.servlet;

import io.ylab.Utils;
import io.ylab.controller.AuthController;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.user.UserDtoRs;
import io.ylab.model.User;
import io.ylab.service.AuthService;
import io.ylab.service.AuthServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthServletTest {
    @Test
    @DisplayName("Тест сервлета аутентификации")
    void testSuccessfulAuthentication() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        ActionRepository actionRepository = Mockito.mock(ActionRepository.class);
        User user = Utils.getUser();
        UserDtoRs expectedUserDtoRs = Utils.getUserDtoRs();
        Mockito.when(userRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(user));
        AuthService authService = new AuthServiceImpl(userRepository, actionRepository);
        AuthController authController = new AuthController(authService);
        UserDtoRs actualUserDtoRs = authController.authenticateUser(user);
        assertEquals(expectedUserDtoRs, actualUserDtoRs);
    }

    @Test
    @DisplayName("Тест негативного сценария аутентификации")
    void testFailedAuthenticationInvalidCredentials() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        ActionRepository actionRepository = Mockito.mock(ActionRepository.class);
        User user = Utils.getUser();
        User wrongPasswordUser = Utils.getUser();
        wrongPasswordUser.setPassword("wrongPassword");
        Mockito.when(userRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(user));
        AuthService authService = new AuthServiceImpl(userRepository, actionRepository);
        AuthController authController = new AuthController(authService);
        UserDtoRs actualUserDtoRs = authController.authenticateUser(wrongPasswordUser);
        assertNull(actualUserDtoRs);
    }
}
