package io.ylab.repository;

import io.ylab.Utils;
import io.ylab.dao.action.JdbcActionRepository;
import io.ylab.exception.NotFoundException;
import io.ylab.model.Action;
import io.ylab.model.User;
import io.ylab.utils.DataBaseConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestContainersActionRepositoryTest {
    private final static String CREATE_SCHEMA_IF_NOT_EXISTS_DOMAIN = "CREATE SCHEMA IF NOT EXISTS domain";
    private final static String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS domain.user" +
            " (user_id SERIAL PRIMARY KEY, user_name VARCHAR(255),password varchar(255),balance decimal)";
    private final static String CREATE_TABLE_QUERY_TWO = "CREATE TABLE IF NOT EXISTS domain.action" +
            " (action_id SERIAL PRIMARY KEY, user_id bigint,activity VARCHAR(255))";
    private final static String INSERT = "INSERT INTO domain.user (user_name,password,balance) VALUES(?,?,?)";
    private final static String NONE_EXIST_USER = "Иван";
    private PostgreSQLContainer<?> container;
    private Connection connection;
    private JdbcActionRepository actionRepository;

    @BeforeEach
    public void setup() throws SQLException {
        container = new PostgreSQLContainer<>("postgres:latest");
        container.start();
        String jdbcUrl = container.getJdbcUrl();
        String username = container.getUsername();
        String password = container.getPassword();
        connection = DataBaseConnector.updateConnectionProperties(jdbcUrl, username, password);
        try (PreparedStatement statement = connection.prepareStatement(CREATE_SCHEMA_IF_NOT_EXISTS_DOMAIN)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, "username");
            statement.setString(2, "password");
            statement.setBigDecimal(3, BigDecimal.valueOf(100));
            statement.executeUpdate();
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY_TWO)) {
            statement.executeUpdate();
        }
        actionRepository = new JdbcActionRepository();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        connection.close();
        container.stop();
    }

    @Test
    @DisplayName("Тест добавления активности")
    void testAddAction() {
        User user = Utils.getUser();
        Action action = Utils.getHistoryAction(user);
        actionRepository.addAction(action);
        List<Action> actions = actionRepository.getAllByUserName(user.getUserName());
        assertTrue(actions.contains(action));
    }

    @Test
    @DisplayName("Тест получения всех активностей по имени юзера")
    void testGetAllByUserName() {
        User user = Utils.getUser();
        Action action1 = Utils.getEnterAction(user);
        Action action2 = Utils.getCreditAction(user);
        actionRepository.addAction(action1);
        actionRepository.addAction(action2);
        List<Action> actions = actionRepository.getAllByUserName(user.getUserName());
        assertEquals(2, actions.size());
        assertThrows(NotFoundException.class, () -> actionRepository.getAllByUserName(NONE_EXIST_USER));
    }

}

