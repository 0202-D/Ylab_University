package io.ylab;

import io.ylab.controller.AuthController;
import io.ylab.controller.UserController;
import io.ylab.dao.transaction.TransactionInMemoryRepository;
import io.ylab.dao.transaction.action.ActionInMemoryRepository;
import io.ylab.dao.transaction.user.UserInMemoryRepository;
import io.ylab.service.AuthService;
import io.ylab.service.AuthServiceImpl;
import io.ylab.service.UserService;
import io.ylab.service.UserServiceImpl;
import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.model.User;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Hello world!
 */
public class WalletService {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInMemoryRepository userInMemoryRepository = new UserInMemoryRepository();
        ActionInMemoryRepository actionInMemoryRepository = new ActionInMemoryRepository();
        AuthService authService = new AuthServiceImpl(userInMemoryRepository,actionInMemoryRepository);
        AuthController authController = new AuthController(authService);
        TransactionInMemoryRepository transactionInMemoryRepository = new TransactionInMemoryRepository();
        UserService userService = new UserServiceImpl(transactionInMemoryRepository, actionInMemoryRepository);
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
                System.out.println("****************");
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
                    System.out.println("Такой операции не существует");
                    continue;
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
                        return;
                    case (1):
                        if (currentUser != null) {
                            userController.balance(currentUser);
                            break;
                        }
                    case (2):
                        System.out.println("Введите сумму");
                        try {
                            int input = scanner.nextInt();
                            BigDecimal sum = new BigDecimal(input);
                            Transaction transaction = Utils.generateTransaction(TransactionalType.DEBIT, sum, currentUser);
                            userController.debit(sum, currentUser, transaction);
                            break;
                        } catch (Exception e) {
                            System.out.println("Не верный ввод");
                            break;
                        }
                    case (3):
                        System.out.println("Введите сумму");
                        try {
                            int input = scanner.nextInt();
                            BigDecimal sum = new BigDecimal(input);
                            Transaction transaction = Utils.generateTransaction(TransactionalType.CREDIT, sum, currentUser);
                            userController.credit(sum, currentUser, transaction);
                            break;
                        } catch (Exception e) {
                            System.out.println("Не верный ввод");
                            break;
                        }
                    case (4):
                        System.out.println(userController.history(currentUser));
                        System.out.println("**********************");
                        break;
                    case (5):
                        System.out.println(userController.activity(currentUser));
                        System.out.println("**********************");
                        break;
                    default:
                        System.out.println("Такой операции не существует");
                        break;
                }
            }
        }
    }

    private static class Utils {
        private static AtomicLong id = new AtomicLong(0);

        static Transaction generateTransaction(TransactionalType transactionalType, BigDecimal sum, User user) {
            long transactionId = id.addAndGet(1);
            return Transaction.builder()
                    .id(new AtomicLong(transactionId))
                    .transactionalType(transactionalType)
                    .sum(sum)
                    .user(user)
                    .build();
        }
    }
}