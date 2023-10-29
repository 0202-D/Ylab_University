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
import io.ylab.model.Activity;
import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.utils.TransactionGenerator;
import org.mapstruct.factory.Mappers;
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
    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    private final ActionMapper actionMapper = Mappers.getMapper(ActionMapper.class);

    public UserServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, ActionRepository actionRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
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
        actionRepository.addAction(new Action(1L, user, Activity.BALANCE));
        return UserBalanceRsDto.builder()
                .userName(user.getUserName())
                .balance(user.getBalance())
                .build();
    }


    /**
     * Метод для списания средств с баланса пользователя.
     *
     * @param sum    - сумма списания.
     * @param userId - объект пользователя, у которого нужно списать средства.
     * @return true, если операция успешно проведена, false - если на балансе недостаточно средств для списания.
     */
    @Override
    public boolean debit(BigDecimal sum, long userId) {
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
            actionRepository.addAction(Action.builder()
                    .user(user)
                    .activity(Activity.CREDIT)
                    .build());
            return true;
        }
    }

    /**
     * Метод для проверки корректности введенных данных.
     *
     * @param sum - сумма, которую нужно проверить.
     * @return true, если введены некорректные данные.
     * @throws RuntimeException если транзакция с таким id уже проводилась.
     */
    private boolean validate(BigDecimal sum) {
        return sum.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Метод для зачисления средств на баланс пользователя.
     *
     * @param sum    - сумма зачисления.
     * @param userId - id пользователя, на баланс которого нужно зачислить средства.
     * @return true, если операция успешно проведена.
     */
    @Override
    public boolean credit(BigDecimal sum, long userId) {
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
            actionRepository.addAction(Action.builder()
                    .user(user)
                    .activity(Activity.CREDIT)
                    .build());
            return true;
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
        actionRepository.addAction(new Action(1L, user, Activity.HISTORY));
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
