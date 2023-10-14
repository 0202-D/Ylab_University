package io.ylab.service;

import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import io.ylab.utils.ConsoleWriter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

/**
 * Реализация интерфейса AuthService, предоставляющая функциональность авторизации и регистрации пользователей.
 */
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository;

    private final ConsoleWriter consoleWriter;

    private final ActionRepository actionRepository;

    /**
     * Метод для добавления нового пользователя.
     *
     * @return true, если пользователь успешно зарегистрирован, false - если пользователь с таким именем уже существует.
     */
    public boolean addUser() {
        String[] nameAndPassword = passwordRequest();
        if (nameAndPassword == null) {
            return false;
        }
        String userName = nameAndPassword[0];
        String password = nameAndPassword[1];
        Optional<User> user = userRepository.getByName(userName);
        if (user.isPresent()) {
            consoleWriter.print("Пользователь с таким именем уже существует!");
            return false;
        } else {
            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setPassword(password);
            newUser.setBalance(new BigDecimal(0));
            newUser = userRepository.addUser(newUser);
            actionRepository.addAction(Action.builder()
                    .user(newUser)
                    .activity(Activity.REGISTERED)
                    .build());
            consoleWriter.print("Вы зарегестрированы!");
            return true;
        }
    }

    /**
     * Метод для аутентификации пользователя.
     *
     * @return объект User, если пользователь успешно аутентифицирован, null - если пользователь не найден или введен неверный пароль.
     */
    public User authenticateUser() {
        String[] nameAndPassword = passwordRequest();
        if (nameAndPassword == null) {
            return null;
        }
        String userName = nameAndPassword[0];
        String password = nameAndPassword[1];
        Optional<User> user = userRepository.getByName(userName);
        if (user.isEmpty()) {
            consoleWriter.print("Такого пользователя не существует!");
            return null;
        }
        if (!user.get().getPassword().equals(password)) {
            consoleWriter.print("Не верный пароль");
            return null;
        }
        consoleWriter.print("Вы вошли");
        actionRepository.addAction(Action.builder()
                .user(user.get())
                .activity(Activity.ENTERED)
                .build());
        return user.get();
    }

    /**
     * Метод для запроса логина и пароля у пользователя.
     *
     * @return массив строк, где первый элемент - логин, а второй - пароль.
     * @throws RuntimeException если введены неверные данные.
     */
    public static String[] passwordRequest() {
        System.out.println("Введите через пробел ваш логин и пароль");
        String input = scanner.nextLine();
        String[] nameAndPassword = input.split(" ");
        if (nameAndPassword.length != 2) {
            System.out.println("Введены не вернуе данные!");
            System.out.println("**********************");
            return null;
        }
        return nameAndPassword;
    }
}
