package io.ylab.dao.transaction;

import io.ylab.dao.user.JdbcUserRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.exception.NotFoundException;
import io.ylab.model.Transaction;
import io.ylab.model.TransactionalType;
import io.ylab.model.User;
import io.ylab.utils.HikariCPDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class JdbcTransactionRepository implements TransactionRepository {
    private static final String INSERT_QUERY = "INSERT INTO domain.transaction (transactional_type,sum,user_id) VALUES(?, ?,?)";
    private static final String GET_QUERY = "SELECT t.transaction_id,t.transactional_type, t.sum, t.user_id " +
            "FROM domain.transaction t " +
            "JOIN domain.user u ON t.user_id = u.user_id " +
            "WHERE u.user_name = ?";
    private static final String GET_BY_ID_QUERY = "Select * from domain.transaction where transaction_id = ?";
    private final UserRepository userRepository = new JdbcUserRepository();


    @Override
    public void addTransaction(Transaction transaction) {
        try (Connection connection = HikariCPDataSource.getConnection(); PreparedStatement statement =
                connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, transaction.getTransactionalType().toString());
            statement.setBigDecimal(2, transaction.getSum());
            statement.setLong(3, transaction.getUser().getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getAllByUserName(String userName) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = userRepository.getByName(userName)
                        .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getLong("transaction_id"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transaction.setTransactionalType(TransactionalType.valueOf(resultSet.getString("transactional_type")));
                transaction.setUser(user);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    @Override
    public Optional<Transaction> getById(Long transactionId) {
        Transaction transaction = new Transaction();
        try (Connection connection = HikariCPDataSource.getConnection();PreparedStatement statement =
                     connection.prepareStatement(GET_BY_ID_QUERY)) {
            statement.setLong(1, transactionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                User user = userRepository.getById(userId)
                        .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
                transaction.setTransactionId(resultSet.getLong("transaction_id"));
                transaction.setSum(resultSet.getBigDecimal("sum"));
                transaction.setTransactionalType(TransactionalType.valueOf(resultSet.getString("transactional_type")));
                transaction.setUser(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(transaction);
    }
}
