package io.ylab.service;

import io.ylab.dao.TransactionRepository;
import io.ylab.dao.UserRepository;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Реализация интерфейса UserService, предоставляющая функциональность работы с балансом пользователей и их транзакциями.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;
    /**
     * Метод для получения баланса пользователя.
     * @param user - объект пользователя, для которого нужно получить баланс.
     */
    @Override
    public void balance(User user) {
        System.out.println("Ваш баланс = " + user.getBalance());
        System.out.println("**********************");
        userRepository.actions.add(new Action(user, Activity.BALANCE));
    }
    /**
     * Метод для списания средств с баланса пользователя.
     * @param sum - сумма списания.
     * @param user - объект пользователя, у которого нужно списать средства.
     * @param transaction - объект транзакции, описывающий проведенную операцию.
     * @return true, если операция успешно проведена, false - если на балансе недостаточно средств для списания.
     */
    @Override
    public boolean debit(BigDecimal sum, User user, Transaction transaction) {
        if (validate(sum, transaction)) return false;
        if (user.getBalance().compareTo(sum) < 0) {
            System.out.println("Средств недостаточно!");
            System.out.println("**********************");
            return false;
        } else {
            user.setBalance(user.getBalance().subtract(sum));
            transactionRepository.transactions.add(transaction);
            userRepository.actions.add(new Action(user, Activity.DEBIT));
            return true;
        }
    }
    /**
     * Метод для проверки корректности введенных данных.
     * @param sum - сумма, которую нужно проверить.
     * @param transaction - объект транзакции, который нужно проверить на уникальность id.
     * @return true, если введены некорректные данные.
     * @throws RuntimeException если транзакция с таким id уже проводилась.
     */
    private boolean validate(BigDecimal sum, Transaction transaction) {
        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Вы ввели отрицательное число");
            System.out.println("**********************");
            return true;
        }
        Optional<Transaction> transactionInRepo = transactionRepository.transactions
                .stream().filter(el -> el.getId() == transaction.getId()).findFirst();
        if (transactionInRepo.isPresent()) {
            System.out.println("Транзакция с таким id была проведена");
            System.out.println("**********************");
            return true;
        }
        return false;
    }
    /**
     * Метод для зачисления средств на баланс пользователя.
     * @param sum - сумма зачисления.
     * @param user - объект пользователя, на баланс которого нужно зачислить средства.
     * @param transaction - объект транзакции, описывающий проведенную операцию.
     * @return true, если операция успешно проведена.
     */
    @Override
    public boolean credit(BigDecimal sum, User user, Transaction transaction) {
        if (validate(sum, transaction)) return false;
        user.setBalance(user.getBalance().add(sum));
        transactionRepository.transactions.add(transaction);
        userRepository.actions.add(new Action(user, Activity.CREDIT));
        return true;
    }
    /**
     * Метод для получения списка транзакций пользователя.
     * @param user - объект пользователя, для которого нужно получить список транзакций.
     * @return список транзакций пользователя.
     */
    @Override
    public List<Transaction> history(User user) {
        List<Transaction> transactions = transactionRepository.transactions
                .stream()
                .filter(el -> el.getUser().getUserName().equals(user.getUserName())).toList();
        userRepository.actions.add(new Action(user, Activity.HISTORY));
        return transactions;
    }
    /**
     * Метод для получения списка действий пользователя.
     * @param currentUser - объект пользователя, для которого нужно получить список действий.
     * @return список действий пользователя.
     */
    @Override
    public List<Action> activity(User currentUser) {
        return userRepository.actions
                .stream().filter(el -> el.getUser().getUserName().equals(currentUser.getUserName()))
                .collect(Collectors.toList());
    }

}
