package io.ylab;

import io.ylab.in.controller.AuthController;
import io.ylab.in.controller.UserController;
import io.ylab.in.dao.UserRepository;
import io.ylab.in.service.AuthService;
import io.ylab.in.service.AuthServiceImpl;
import io.ylab.in.service.UserService;
import io.ylab.in.service.UserServiceImpl;
import io.ylab.model.User;

import java.util.Scanner;

/**
 * Hello world!
 */
public class WalletService {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthServiceImpl(userRepository);
        AuthController authController = new AuthController(authService);
        UserService userService = new UserServiceImpl();
        User currentUser = null;
        UserController userController = new UserController(userService);


        while (true) {
            System.out.println("Добро пожаловать!");
            System.out.println("Чтобы зарегестрироваться введите 1");
            System.out.println("Чтобы войти введите 2");
            System.out.println("Чтобы выйти введите 0");
            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Не верный ввод");
                break;
            }
            switch (choice) {
                case (0):
                    break;
                case (1):
                    authController.addUser();
                    continue;
                case (2):
                    currentUser = authController.authenticateUser();
                    break;
                default:
                    System.out.println("Такой операции не существует");
                    break;
            }
              while (true) {
                  System.out.println("Добро пожаловать в ваш профиль!");
                  System.out.println("Посмотреть баланс введите 1");
                  System.out.println("Снять введите 2");
                  System.out.println("Пополнить введите 3");
                  System.out.println("История введите 4");
                  System.out.println("Ваши действия нажмите 5");
                  System.out.println("Чтобы выйти введите 0");
                  int choiceTwo = 0;
                  try {
                      choiceTwo = scanner.nextInt();
                  } catch (Exception e) {
                      System.out.println("Не верный ввод");
                      break;
                  }
                  switch (choiceTwo) {
                      case (0):
                          break;
                      case (1):
                          if (currentUser != null) {
                              userController.balance(currentUser);
                          }
                  }
              }
        }
    }
}