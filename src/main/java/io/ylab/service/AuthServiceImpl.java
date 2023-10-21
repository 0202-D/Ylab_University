package io.ylab.service;

import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

/**
 * Реализация интерфейса AuthService, предоставляющая функциональность авторизации и регистрации пользователей.
 */
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final ActionRepository actionRepository;

    /**
     * Метод для добавления нового пользователя.
     *
     * @return true, если пользователь успешно зарегистрирован, false - если пользователь с таким именем уже существует.
     */
    public User addUser(User user) {
        Optional<User> optionalUser = userRepository.getByName(user.getUserName());
        if (optionalUser.isPresent()) {
            return null;
        } else {
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
    }

    /**
     * Метод для аутентификации пользователя.
     *
     * @return объект User, если пользователь успешно аутентифицирован, null - если пользователь не найден или введен неверный пароль.
     */
    public User authenticateUser(User user) {


        Optional<User> optionalUser = userRepository.getByName(user.getUserName());
        if (optionalUser.isEmpty()) {
            return null;
        }
        if (!optionalUser.get().getPassword().equals(user.getPassword())) {
            return null;
        }
        actionRepository.addAction(Action.builder()
                .user(optionalUser.get())
                .activity(Activity.ENTERED)
                .build());
        return optionalUser.get();
    }
}
