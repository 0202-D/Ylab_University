package io.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import io.ylab.controller.AuthController;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.action.JdbcActionRepository;
import io.ylab.dao.user.JdbcUserRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.user.UserRqDto;
import io.ylab.exception.ExceptionJson;
import io.ylab.mapper.user.UserMapper;
import io.ylab.service.AuthService;
import io.ylab.service.AuthServiceImpl;
import org.mapstruct.factory.Mappers;

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

import static io.ylab.servlet.UserServlet.APPLICATION_JSON;


public class RegistrationServlet extends HttpServlet {
    private static final String BAD_REQUEST_MESSAGE = "Пользователь с таким именем уже существует";
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private Validator validator;
    private final ObjectMapper objectMapper;
    private final AuthController authController;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final AuthService authService;


    public RegistrationServlet() {
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
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        var body = req.getReader();
        final var userDto = gson.fromJson(body, UserRqDto.class);
        Set<ConstraintViolation<UserRqDto>> violations = validator.validate(userDto);
        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<UserRqDto> violation : violations) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ExceptionJson exceptionJson = ExceptionJson.builder()
                        .message(violation.getMessage())
                        .httpResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .build();
                resp.setContentType(APPLICATION_JSON);
                resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
            }
        }
        else {
        var user = mapper.toEntity(userDto);
        var userDtoRs = authController.addUser(user);
        if (userDtoRs != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(APPLICATION_JSON);
            resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(userDtoRs));
        } else {
            ExceptionJson exceptionJson = ExceptionJson.builder()
                    .message(BAD_REQUEST_MESSAGE)
                    .httpResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .build();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType(APPLICATION_JSON);
            resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));}
        }
    }
}
