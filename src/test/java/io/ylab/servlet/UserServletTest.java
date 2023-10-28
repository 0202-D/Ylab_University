package io.ylab.servlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

 class UserServletTest {
    @Mock
    private UserServlet userServlet;
    @Test
    @DisplayName("Тест метода балансе")
     void testGetUserBalanceReturnsUserBalance() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(response.getOutputStream()).thenReturn(outputStream);
        when(request.getRequestURI()).thenReturn("/user/balance/1");
        when(request.getPathInfo()).thenReturn("/1");
        UserServlet userServlet = new UserServlet();
        userServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType(UserServlet.APPLICATION_JSON);
    }
}
