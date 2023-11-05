package io.ylab.service;

import io.ylab.loggableaspectstarter.aop.annotation.Loggable;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.user.UserRqDto;
import io.ylab.exception.IncorrectDataException;
import io.ylab.exception.NotFoundException;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Реализация интерфейса AuthService, предоставляющая функциональность авторизации и регистрации пользователей.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final String USER_NOT_FOUND = "Такого ползователя не существует";

    private static final String USER_EXISTS = "Такой пользователь уже существует";
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;

    public AuthServiceImpl(UserRepository userRepository, ActionRepository actionRepository) {
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
    }

    /**
     * Метод для добавления нового пользователя.
     *
     * @return объект User, если пользователь успешно аутентифицирован
     */
    public User addUser(UserRqDto user) {
        Optional<User> findUser = userRepository.getByName(user.getUserName());
        if (findUser.isPresent()) {
            throw new IncorrectDataException(USER_EXISTS);
        }
        User newUser = User.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .balance(new BigDecimal(0))
                .build();
        newUser = userRepository.addUser(newUser);
        actionRepository.addAction(Action.builder()
                .user(newUser)
                .activity(Activity.REGISTERED)
                .build());
        return newUser;
    }

    /**
     * Метод для аутентификации пользователя.
     *
     * @return объект User, если пользователь успешно аутентифицирован
     */
    @Loggable
    public User authenticateUser(UserRqDto user) {
        User findUser = userRepository.getByName(user.getUserName())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!findUser.getPassword().equals(user.getPassword())) {
            throw new IncorrectDataException("Не верный пароль");
        }
        actionRepository.addAction(Action.builder()
                .user(findUser)
                .activity(Activity.ENTERED)
                .build());
        return findUser;
    }
}
