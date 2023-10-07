package io.ylab.in.service;

import io.ylab.in.dao.TransactionRepository;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TransactionRepository transactionRepository;

    @Override
    public void balance(User user) {
        System.out.println("Ваш баланс = " + user.getBalance());
        System.out.println("**********************");
    }

    @Override
    public boolean debit(BigDecimal sum, User user, Transaction transaction) {
        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Вы ввели отрицательное число");
            return false;
        }
        Optional<Transaction> transactionInRepo = transactionRepository.transaction
                .stream().filter(el -> el.getId() == transaction.getId()).findFirst();
        if (transactionInRepo.isPresent()) {
            throw new RuntimeException("Транзакция с таким id была проведена");
        }
        if (user.getBalance().compareTo(sum) < 0) {
            System.out.println("Средств недостаточно!");
            System.out.println("**********************");
            return false;
        } else {
            user.setBalance(user.getBalance().subtract(sum));
            transactionRepository.transaction.add(transaction);
            return true;
        }
    }

    @Override
    public boolean credit(BigDecimal sum, User user, Transaction transaction) {
        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Вы ввели отрицательное число");
            return false;
        }
        Optional<Transaction> transactionInRepo = transactionRepository.transaction
                .stream().filter(el -> el.getId() == transaction.getId()).findFirst();
        if (transactionInRepo.isPresent()) {
            throw new RuntimeException("Транзакция с таким id была проведена");
        }
        user.setBalance(user.getBalance().add(sum));
        transactionRepository.transaction.add(transaction);
        return true;
    }

    @Override
    public List<Transaction> history(User user) {
        List<Transaction> transactions = transactionRepository.transaction
                .stream()
                .filter(el -> el.getUser().getUserName().equals(user.getUserName())).toList();
        return transactions;
    }

}
