package io.ylab.service;

import io.ylab.dao.UserRepository;
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
    private static final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository;

    /**
     * Метод для добавления нового пользователя.
     *
     * @return true, если пользователь успешно зарегистрирован, false - если пользователь с таким именем уже существует.
     */
    public boolean addUser() {
        String[] nameAndPassword = passwordRequest();
        String userName = nameAndPassword[0];
        String password = nameAndPassword[1];
        Optional<User> user = userRepository.users.stream().filter(e -> e.getUserName().equals(userName)).findFirst();
        if (user.isPresent()) {
            System.out.println("Пользователь с таким именем уже существует!");
            System.out.println("Такого пользователя не существует!");
            return false;
        } else {
            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setPassword(password);
            newUser.setBalance(new BigDecimal(0));
            userRepository.addUser(newUser);
            userRepository.addAction(new Action(newUser, Activity.REGISTERED));
            System.out.println("Вы зарегестрированы!");
            System.out.println("**********************");
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
        Optional<User> user = userRepository.users.stream().filter(e -> e.getUserName().equals(userName)).findFirst();
        if (user.isEmpty()) {
            System.out.println("Такого пользователя не существует!");
            System.out.println("**********************");
            return null;
        }
        if (!user.get().getPassword().equals(password)) {
            System.out.println("Не верный пароль");
            System.out.println("**********************");
            return null;
        }

        System.out.println("Вы вошли");
        System.out.println("**********************");
        userRepository.addAction(new Action(user.get(), Activity.ENTERED));
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
