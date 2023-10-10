package io.ylab.service;

import io.ylab.dao.transaction.TransactionInMemoryRepository;
import io.ylab.dao.action.ActionInMemoryRepository;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import io.ylab.utils.ConsoleWriter;
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

    private final TransactionInMemoryRepository transactionInMemoryRepository;

    private final ActionInMemoryRepository actionInMemoryRepository;

    private final ConsoleWriter consoleWriter;

    /**
     * Метод для получения баланса пользователя.
     *
     * @param user - объект пользователя, для которого нужно получить баланс.
     */
    @Override
    public void balance(User user) {
        consoleWriter.printBalance(user.getBalance());
        actionInMemoryRepository.addAction(new Action(user, Activity.BALANCE));
    }

    /**
     * Метод для списания средств с баланса пользователя.
     *
     * @param sum         - сумма списания.
     * @param user        - объект пользователя, у которого нужно списать средства.
     * @param transaction - объект транзакции, описывающий проведенную операцию.
     * @return true, если операция успешно проведена, false - если на балансе недостаточно средств для списания.
     */
    @Override
    public boolean debit(BigDecimal sum, User user, Transaction transaction) {
        if (validate(sum, transaction)) return false;
        if (user.getBalance().compareTo(sum) < 0) {
           consoleWriter.print("Средств недостаточно");
            return false;
        } else {
            user.setBalance(user.getBalance().subtract(sum));
            transactionInMemoryRepository.addTransaction(transaction);
            actionInMemoryRepository.addAction(new Action(user, Activity.DEBIT));
            return true;
        }
    }

    /**
     * Метод для проверки корректности введенных данных.
     *
     * @param sum         - сумма, которую нужно проверить.
     * @param transaction - объект транзакции, который нужно проверить на уникальность id.
     * @return true, если введены некорректные данные.
     * @throws RuntimeException если транзакция с таким id уже проводилась.
     */
    private boolean validate(BigDecimal sum, Transaction transaction) {
        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            consoleWriter.print("Вы ввели отрицательное число");
            return true;
        }
        Optional<Transaction> transactionInRepo = transactionInMemoryRepository.transactions
                .stream().filter(el -> el.getTransactionId() == transaction.getTransactionId()).findFirst();
        if (transactionInRepo.isPresent()) {
            consoleWriter.print("Транзакция с таким id была проведена");
            return true;
        }
        return false;
    }

    /**
     * Метод для зачисления средств на баланс пользователя.
     *
     * @param sum         - сумма зачисления.
     * @param user        - объект пользователя, на баланс которого нужно зачислить средства.
     * @param transaction - объект транзакции, описывающий проведенную операцию.
     * @return true, если операция успешно проведена.
     */
    @Override
    public boolean credit(BigDecimal sum, User user, Transaction transaction) {
        if (validate(sum, transaction)) return false;
        user.setBalance(user.getBalance().add(sum));
        transactionInMemoryRepository.transactions.add(transaction);
        actionInMemoryRepository.actions.add(new Action(user, Activity.CREDIT));
        return true;
    }

    /**
     * Метод для получения списка транзакций пользователя.
     *
     * @param user - объект пользователя, для которого нужно получить список транзакций.
     * @return список транзакций пользователя.
     */
    @Override
    public List<Transaction> history(User user) {
        List<Transaction> transactions = transactionInMemoryRepository.transactions
                .stream()
                .filter(el -> el.getUser().getUserName().equals(user.getUserName())).toList();
        actionInMemoryRepository.actions.add(new Action(user, Activity.HISTORY));
        return transactions;
    }

    /**
     * Метод для получения списка действий пользователя.
     *
     * @param currentUser - объект пользователя, для которого нужно получить список действий.
     * @return список действий пользователя.
     */
    @Override
    public List<Action> activity(User currentUser) {
        return actionInMemoryRepository.actions
                .stream().filter(el -> el.getUser().getUserName().equals(currentUser.getUserName()))
                .collect(Collectors.toList());
    }

}
