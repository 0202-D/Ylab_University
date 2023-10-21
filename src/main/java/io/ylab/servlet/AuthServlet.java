package io.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.ylab.controller.AuthController;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.action.JdbcActionRepository;
import io.ylab.dao.user.JdbcUserRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.exception.ExceptionJson;
import io.ylab.service.AuthService;
import io.ylab.service.AuthServiceImpl;
import io.ylab.utils.LiquibaseStarter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private static final String NOT_FOUND = "Такого пользователя не существует";
    private final ObjectMapper objectMapper;
    private final AuthController authController;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final AuthService authService;


    public AuthServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.actionRepository = new JdbcActionRepository();
        this.userRepository = new JdbcUserRepository();
        this.authService = new AuthServiceImpl(userRepository, actionRepository);
        this.authController = new AuthController(authService);
    }

    @Override
    public void init() throws ServletException {

        super.init();
        try {
            LiquibaseStarter liquibaseStarter = new LiquibaseStarter();
            liquibaseStarter.createLiquibase();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var userDtoRs = authController.authenticateUser(req.getReader(), resp);
        if (userDtoRs != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(userDtoRs));
        } else {
            ExceptionJson exceptionJson = ExceptionJson.builder()
                    .message(NOT_FOUND)
                    .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                    .build();
            resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
        }
    }
}