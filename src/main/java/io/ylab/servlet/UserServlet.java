package io.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import io.ylab.controller.UserController;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.action.JdbcActionRepository;
import io.ylab.dao.transaction.JdbcTransactionRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.dao.user.JdbcUserRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.activity.ActivityRs;
import io.ylab.dto.transaction.CreditAndDebitRq;
import io.ylab.dto.transaction.CreditAndDebitRs;
import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;
import io.ylab.exception.ExceptionJson;
import io.ylab.security.JwtProvider;
import io.ylab.service.UserService;
import io.ylab.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserServlet extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";
    private static final String NOT_FOUND = "Такого пользователя не существует";

    private static final String ACCESS_DENIED = "Доступ запрещен!";
    private JwtProvider jwtProvider = new JwtProvider();
    private final ObjectMapper objectMapper;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final UserController userController;
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    private Validator validator;

    public UserServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.actionRepository = new JdbcActionRepository();
        this.userRepository = new JdbcUserRepository();
        this.transactionRepository = new JdbcTransactionRepository();
        this.userService = new UserServiceImpl(transactionRepository, userRepository, actionRepository);
        this.userController = new UserController(userService);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!jwtProvider.validateJwtToken(req)) {
            returnForbiddenResponse(resp);
        } else {
            String requestURI = req.getRequestURI();
            if (requestURI.contains("user/balance")) {
                long userId = getUserId(req);
                UserBalanceRs userBalanceRs = userController.balance(userId);
                if (userBalanceRs == null) {
                    ExceptionJson exceptionJson = ExceptionJson.builder()
                            .message(NOT_FOUND)
                            .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                            .build();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(userBalanceRs));
                }
            } else if (requestURI.contains("/user/history/")) {
                long userId = getUserId(req);
                List<TransactionHistoryDtoRs> history = userController.history(userId);
                if (history == null) {
                    ExceptionJson exceptionJson = ExceptionJson.builder()
                            .message(NOT_FOUND)
                            .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                            .build();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(history));
                }
            } else if (requestURI.contains("/user/activity")) {
                long userId = getUserId(req);
                List<ActivityRs> activity = userController.activity(userId);
                if (activity == null) {
                    ExceptionJson exceptionJson = ExceptionJson.builder()
                            .message(NOT_FOUND)
                            .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                            .build();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(activity));
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!jwtProvider.validateJwtToken(req)) {
            returnForbiddenResponse(resp);
        } else {
            String requestURI = req.getRequestURI();
            if (requestURI.contains("/user/credit")) {
                resp.setContentType(APPLICATION_JSON);
                final var gson = new Gson();
                var body = req.getReader();
                final var creditRq = gson.fromJson(body, CreditAndDebitRq.class);
                Set<ConstraintViolation<CreditAndDebitRq>> violations = validator.validate(creditRq);
                if (!violations.isEmpty()) {
                    List<String> errors = new ArrayList<>();
                    for (ConstraintViolation<CreditAndDebitRq> violation : violations) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        ExceptionJson exceptionJson = ExceptionJson.builder()
                                .message(violation.getMessage())
                                .httpResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .build();
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                    }
                } else {
                    boolean isOk = userController.credit(creditRq);
                    if (!isOk) {
                        CreditAndDebitRs creditAndDebitRs = CreditAndDebitRs.builder()
                                .httpResponse(HttpServletResponse.SC_BAD_REQUEST).build();
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(creditAndDebitRs));
                    } else {
                        CreditAndDebitRs creditAndDebitRs = CreditAndDebitRs.builder()
                                .httpResponse(HttpServletResponse.SC_OK).build();
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(creditAndDebitRs));
                    }
                }
            }
            if (requestURI.contains("/user/debit")) {
                resp.setContentType(APPLICATION_JSON);
                final var gson = new Gson();
                var body = req.getReader();
                final var debitRq = gson.fromJson(body, CreditAndDebitRq.class);
                Set<ConstraintViolation<CreditAndDebitRq>> violations = validator.validate(debitRq);
                if (!violations.isEmpty()) {
                    List<String> errors = new ArrayList<>();
                    for (ConstraintViolation<CreditAndDebitRq> violation : violations) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        ExceptionJson exceptionJson = ExceptionJson.builder()
                                .message(violation.getMessage())
                                .httpResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .build();
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                    }
                } else {
                    boolean isOk = userController.debit(debitRq);
                    if (!isOk) {
                        CreditAndDebitRs creditAndDebitRs = CreditAndDebitRs.builder()
                                .httpResponse(HttpServletResponse.SC_BAD_REQUEST).build();
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(creditAndDebitRs));
                    } else {
                        CreditAndDebitRs creditAndDebitRs = CreditAndDebitRs.builder()
                                .httpResponse(HttpServletResponse.SC_OK).build();
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(creditAndDebitRs));
                    }
                }
            }
        }
    }

    private static long getUserId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        int lastIndexOfSlash = pathInfo.lastIndexOf('/');
        String pathVarible = pathInfo.substring(lastIndexOfSlash + 1);
        long userId = Long.parseLong(pathVarible);
        return userId;
    }

    private void returnForbiddenResponse(HttpServletResponse resp) throws IOException {
        ExceptionJson exceptionJson = ExceptionJson.builder()
                .message(ACCESS_DENIED)
                .httpResponse(HttpServletResponse.SC_FORBIDDEN)
                .build();
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType(APPLICATION_JSON);
        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
    }
}
