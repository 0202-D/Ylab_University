package io.ylab.service;

import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.transaction.TransactionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
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
        var user = userRepository.getById(userId);
        if (!user.isPresent()) {
            return null;
        } else {
            var findUser = user.get();
            actionRepository.addAction(new Action(1L, findUser, Activity.BALANCE));
            return UserBalanceRsDto.builder()
                    .userName(findUser.getUserName())
                    .balance(findUser.getBalance())
                    .build();
        }
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
        var user = userRepository.getById(userId);
        if (user.isEmpty() || validate(sum)) {
            return false;
        }
        var findUser = user.get();
        if (findUser.getBalance().compareTo(sum) < 0) {
            return false;
        } else {
            findUser.setBalance(findUser.getBalance().subtract(sum));
            userRepository.updateBalance(findUser.getUserId(), findUser.getBalance());
            Transaction transaction = TransactionGenerator
                    .generateTransaction(TransactionalType.DEBIT, sum, findUser);
            transactionRepository.addTransaction(transaction);
            actionRepository.addAction(Action.builder()
                    .user(findUser)
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
        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            return true;
        }
        return false;
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
        var user = userRepository.getById(userId);
        if (user.isEmpty() || validate(sum)) {
            return false;
        } else {
            var findUser = user.get();
            findUser.setBalance(findUser.getBalance().add(sum));
            userRepository.updateBalance(findUser.getUserId(), findUser.getBalance());
            Transaction transaction = TransactionGenerator
                    .generateTransaction(TransactionalType.CREDIT, sum, findUser);
            transactionRepository.addTransaction(transaction);
            actionRepository.addAction(Action.builder()
                    .user(findUser)
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
        var user = userRepository.getById(userId);
        if (!user.isPresent()) {
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
     * @param userId - id пользователя, для которого нужно получить список действий.
     * @return список действий пользователя.
     */
    @Override
    public List<ActivityRsDto> activity(long userId) {
        var user = userRepository.getById(userId);
        if (!user.isPresent()) {
            return null;
        }
        var findUser = user.get();
        List<Action> actions = actionRepository.getAllByUserName(findUser.getUserName());
        return actions.stream().map(a -> actionMapper.toDtoRs(a)).collect(Collectors.toList());
    }

}
