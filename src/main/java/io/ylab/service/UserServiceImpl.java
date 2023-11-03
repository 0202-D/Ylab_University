package io.ylab.service;

import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.exception.IncorrectDataException;
import io.ylab.exception.NotFoundException;
import io.ylab.mapper.action.ActionMapper;
import io.ylab.mapper.transaction.TransactionMapper;
import io.ylab.model.Action;
import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.utils.TransactionGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса UserService, предоставляющая функциональность работы с балансом пользователей и их транзакциями.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "Такого ползователя не существует";
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
    private final TransactionMapper transactionMapper;
    private final ActionMapper actionMapper;

    public UserServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, ActionRepository actionRepository, TransactionMapper transactionMapper, ActionMapper actionMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
        this.transactionMapper = transactionMapper;
        this.actionMapper = actionMapper;
    }

    /**
     * Метод для получения баланса пользователя.
     *
     * @param userId - id пользователя, для которого нужно получить баланс.
     */
    @Override
    public UserBalanceRsDto balance(long userId) {
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return UserBalanceRsDto.builder()
                .userName(user.getUserName())
                .balance(user.getBalance())
                .build();
    }

    /**
     * Метод для списания средств с баланса пользователя.
     *
     * @param sum    - сумма списания.
     * @param userId - идентификатор пользователя, у которого нужно списать средства.
     */
    @Override
    public void debit(BigDecimal sum, long userId) {
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (validate(sum)) {
            throw new IncorrectDataException("Сумма должна быть положительная");
        }
        if (user.getBalance().compareTo(sum) < 0) {
            throw new IncorrectDataException("Не достаточно средств");
        } else {
            user.setBalance(user.getBalance().subtract(sum));
            userRepository.updateBalance(user.getUserId(), user.getBalance());
            Transaction transaction = TransactionGenerator
                    .generateTransaction(TransactionalType.DEBIT, sum, user);
            transactionRepository.addTransaction(transaction);
        }
    }

    /**
     * Метод для проверки корректности введенных данных.
     *
     * @param sum - сумма, которую нужно проверить.
     * @return true, если введены некорректные данные.
     */
    private boolean validate(BigDecimal sum) {
        return sum.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Метод для зачисления средств на баланс пользователя.
     *
     * @param sum    - сумма зачисления.
     * @param userId - id пользователя, на баланс которого нужно зачислить средства.
     */
    @Override
    public void credit(BigDecimal sum, long userId) {
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (validate(sum)) {
            throw new IncorrectDataException("Сумма должна быть положительная");
        } else {
            user.setBalance(user.getBalance().add(sum));
            userRepository.updateBalance(user.getUserId(), user.getBalance());
            Transaction transaction = TransactionGenerator
                    .generateTransaction(TransactionalType.CREDIT, sum, user);
            transactionRepository.addTransaction(transaction);
        }
    }

    /**
     * Метод для получения списка транзакций пользователя.
     *
     * @param userId - id пользователя, для которого нужно получить список транзакций.
     * @return список транзакций пользователя.
     */
    @Override
    public List<TransactionHistoryRsDto> history(long userId) {
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<Transaction> transactions = transactionRepository.getAllByUserName(user.getUserName());
        return transactions.stream().map(transactionMapper::toDtoRs).collect(Collectors.toList());
    }

    /**
     * Метод для получения списка действий пользователя.
     *
     * @param userId - id пользователя, для которого нужно получить список действий.
     * @return список действий пользователя.
     */
    @Override
    public List<ActivityRsDto> activity(long userId) {
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<Action> actions = actionRepository.getAllByUserName(user.getUserName());
        return actions.stream().map(actionMapper::toDtoRs).collect(Collectors.toList());
    }

}
