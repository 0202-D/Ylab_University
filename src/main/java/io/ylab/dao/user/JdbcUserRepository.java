package io.ylab.dao.user;

import io.ylab.exception.NotFoundException;
import io.ylab.model.User;
import io.ylab.utils.HikariCPDataSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
@Repository
public class JdbcUserRepository implements UserRepository {
    private static final String INSERT_QUERY = "INSERT INTO domain.user (user_name,password,balance) VALUES(?, ?, ?)";
    private static final String GET_BY_NAME_QUERY = "SELECT u.* from domain.user u where u.user_name = ?";
    private static final String GET_BY_ID_QUERY = "SELECT u.* from domain.user u where u.user_id = ?";
    private static final String UPDATE_QUERY = "UPDATE domain.user SET balance = ? WHERE user_id = ?";

    @Override
    public User addUser(User user) {
        try (Connection connection = HikariCPDataSource.getConnection(); PreparedStatement statement =
                connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setBigDecimal(3, user.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getByName(user.getUserName())
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует!"));
    }

    @Override
    public Optional<User> getByName(String name) {
        User user = new User();
        try (Connection connection = HikariCPDataSource.getConnection(); PreparedStatement statement =
                connection.prepareStatement(GET_BY_NAME_QUERY)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getLong("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("password"));
                user.setBalance(resultSet.getBigDecimal("balance"));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = new User();
        try (Connection connection = HikariCPDataSource.getConnection(); PreparedStatement statement =
                connection.prepareStatement(GET_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getLong("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("password"));
                user.setBalance(resultSet.getBigDecimal("balance"));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(user);
    }

    @Override
    public void updateBalance(long userId, BigDecimal balance) {
        try (Connection connection = HikariCPDataSource.getConnection();PreparedStatement statement =
                     connection.prepareStatement(UPDATE_QUERY)) {
            statement.setBigDecimal(1, balance);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}