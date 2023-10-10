package io.ylab;

import io.ylab.controller.AuthController;
import io.ylab.controller.UserController;
import io.ylab.dao.action.ActionInMemoryRepository;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.transaction.TransactionInMemoryRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.dao.user.UserInMemoryRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.model.User;
import io.ylab.service.AuthService;
import io.ylab.service.AuthServiceImpl;
import io.ylab.service.UserService;
import io.ylab.service.UserServiceImpl;
import io.ylab.utils.ConsoleWriter;
import io.ylab.utils.TransactionGenerator;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Hello world!
 */
public class WalletService {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleWriter consoleWriter = new ConsoleWriter();
        UserRepository userRepository = new UserInMemoryRepository();
        ActionRepository actionRepository = new ActionInMemoryRepository();
        AuthService authService = new AuthServiceImpl(userRepository,consoleWriter,actionRepository);
        AuthController authController = new AuthController(authService);
        TransactionRepository transactionRepository = new TransactionInMemoryRepository();
        UserService userService = new UserServiceImpl(transactionRepository, actionRepository,consoleWriter);
        User currentUser;
        UserController userController = new UserController(userService);
        TransactionGenerator transactionGenerator = new TransactionGenerator();

        while (true) {
            consoleWriter.print("Добро пожаловать!");
            consoleWriter.print("Чтобы зарегестрироваться введите 1");
            consoleWriter.print("Чтобы войти введите 2");
            consoleWriter.print("Чтобы выйти введите 0");
            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                consoleWriter.print("Не верный ввод");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case (0):
                    continue;
                case (1):
                    authController.addUser();
                    continue;
                case (2):
                    currentUser = authController.authenticateUser();
                    if (currentUser == null) {
                        continue;
                    }
                    break;
                default:
                    consoleWriter.print("Такой операции не существует");
                    continue;
            }
            while (true) {
                consoleWriter.print("Добро пожаловать в ваш профиль!");
                consoleWriter.print("Посмотреть баланс введите 1");
                consoleWriter.print("Снять введите 2");
                consoleWriter.print("Пополнить введите 3");
                consoleWriter.print("История введите 4");
                consoleWriter.print("Ваши действия нажмите 5");
                consoleWriter.print("Чтобы выйти введите 0");
                int choiceTwo = 0;
                try {
                    choiceTwo = scanner.nextInt();
                } catch (Exception e) {
                    consoleWriter.print("Не верный ввод");
                    break;
                }
                switch (choiceTwo) {
                    case (0):
                        return;
                    case (1):
                        if (currentUser != null) {
                            userController.balance(currentUser);
                            break;
                        }
                    case (2):
                        consoleWriter.print("Введите сумму");
                        try {
                            int input = scanner.nextInt();
                            BigDecimal sum = new BigDecimal(input);
                            Transaction transaction = transactionGenerator.generateTransaction(TransactionalType.DEBIT, sum, currentUser);
                            userController.debit(sum, currentUser, transaction);
                            break;
                        } catch (Exception e) {
                            consoleWriter.print("Не верный ввод");
                            continue;
                        }
                    case (3):
                        consoleWriter.print("Введите сумму");
                        try {
                            int input = scanner.nextInt();
                            BigDecimal sum = new BigDecimal(input);
                            Transaction transaction = transactionGenerator.generateTransaction(TransactionalType.CREDIT, sum, currentUser);
                            userController.credit(sum, currentUser, transaction);
                            break;
                        } catch (Exception e) {
                            consoleWriter.print("Не верный ввод");
                            continue;
                        }
                    case (4):
                        System.out.println(userController.history(currentUser));
                        break;
                    case (5):
                        System.out.println(userController.activity(currentUser));
                        break;
                    default:
                        consoleWriter.print("Такой операции не существует");
                        break;
                }
            }
        }
    }


}