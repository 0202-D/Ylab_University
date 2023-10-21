package io.ylab.service;

import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.transaction.TransactionHistoryDtoRs;
import io.ylab.dto.transaction.UserBalanceRs;
import io.ylab.mapper.transaction.TransactionMapper;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

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
    private final ActionRepository actionRepository;

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    /**
     * Метод для получения баланса пользователя.
     *
     * @param userId - id пользователя, для которого нужно получить баланс.
     */
    @Override
    public UserBalanceRs balance(long userId) {
        var user = userRepository.getById(userId);
        if (!user.isPresent()) {
            return null;
        } else {
            var findUser = user.get();
            actionRepository.addAction(new Action(1L, findUser, Activity.BALANCE));
            return UserBalanceRs.builder()
                    .userName(findUser.getUserName())
                    .balance(findUser.getBalance())
                    .build();
        }
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
            return false;
        } else {
            user.setBalance(user.getBalance().subtract(sum));
            userRepository.updateBalance(user.getUserId(), user.getBalance());
            transactionRepository.addTransaction(transaction);
            actionRepository.addAction(new Action(1L, user, Activity.DEBIT));
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
            return true;
        }
        Optional<Transaction> transactionInRepo = transactionRepository.getById(transaction.getTransactionId());
        if (transactionInRepo.isPresent()) {
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
        userRepository.updateBalance(user.getUserId(), user.getBalance());
        transactionRepository.addTransaction(transaction);
        actionRepository.addAction(new Action(1L, user, Activity.CREDIT));
        return true;
    }

    /**
     * Метод для получения списка транзакций пользователя.
     *
     * @param userId - id пользователя, для которого нужно получить список транзакций.
     * @return список транзакций пользователя.
     */
    @Override
    public List<TransactionHistoryDtoRs> history(long userId) {
        var user = userRepository.getById(userId);
        if(!user.isPresent()){
            return null;
        }
        var findUser = user.get();
        List<Transaction> transactions = transactionRepository.getAllByUserName(findUser.getUserName());
        actionRepository.addAction(new Action(1L, findUser, Activity.HISTORY));
        return transactions.stream().map(transactionMapper::toDtoRs).collect(Collectors.toList());
    }

    /**
     * Метод для получения списка действий пользователя.
     *
     * @param currentUser - объект пользователя, для которого нужно получить список действий.
     * @return список действий пользователя.
     */
    @Override
    public List<Action> activity(User currentUser) {
        return actionRepository.getAllByUserName(currentUser.getUserName());
    }

}
