package io.ylab.dao.user;

import io.ylab.model.User;
import io.ylab.utils.DataBaseConnector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    Connection connection = DataBaseConnector.getConnection();

    @Override
    public User addUser(User user) {
        String query = "INSERT INTO domain.user (user_name,password,balance) VALUES(?, ?, ?)";
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setBigDecimal(3, user.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getByName(user.getUserName())
                .orElseThrow(() -> new RuntimeException("Такого пользователя не существует!"));
    }

    @Override
    public Optional<User> getByName(String name) {
        String query = "SELECT u.* from domain.user u where u.user_name = ?";
        User user = new User();
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
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
        String query = "SELECT u.* from domain.user u where u.user_id = ?";
        User user = new User();
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
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
        String query = "UPDATE domain.user SET balance = ? WHERE user_id = ?";
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setBigDecimal(1, balance);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}