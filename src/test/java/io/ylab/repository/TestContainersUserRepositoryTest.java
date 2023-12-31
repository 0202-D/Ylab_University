package io.ylab.repository;

import io.ylab.Utils;
import io.ylab.dao.user.JdbcUserRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class TestContainersUserRepositoryTest {
        private final static String CREATE_SCHEMA_IF_NOT_EXISTS_DOMAIN = "CREATE SCHEMA IF NOT EXISTS domain";
        private final static String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS domain.user" +
                " (user_id SERIAL PRIMARY KEY, user_name VARCHAR(255),password varchar(255),balance decimal)";
        private PostgreSQLContainer<?> container;
        private Connection connection;
        private JdbcUserRepository userRepository;

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
            userRepository = new JdbcUserRepository();
        }

        @AfterEach
        public void cleanup() throws SQLException {
            connection.close();
            container.stop();
        }

        @Test
        @DisplayName("Тестирование метода добавления пользователя")
        void testAddUser() {
            User user = Utils.getUser();
            User addedUser = userRepository.addUser(user);
            assertEquals(user.getUserName(), addedUser.getUserName());
            assertEquals(user.getPassword(), addedUser.getPassword());
            assertEquals(user.getBalance(), addedUser.getBalance());
        }

        @Test
        @DisplayName("Тестирование метода изменения баланса пользователя")
        void testUpdateBalance() {
            User user = Utils.getUser();
            user = userRepository.addUser(user);
            BigDecimal newBalance = BigDecimal.valueOf(20);
            userRepository.updateBalance(user.getUserId(), newBalance);
            Optional<User> optionalUser = userRepository.getById(user.getUserId());
            assertTrue(optionalUser.isPresent());
            User retrievedUser = optionalUser.get();
            assertEquals(newBalance, retrievedUser.getBalance());
        }

        @Test
        @DisplayName("Тестирование метода получения юзера по его id")
        void testGetById() {
            User user = Utils.getUser();
            user = userRepository.addUser(user);
            Optional<User> optionalUser = userRepository.getById(user.getUserId());
            assertTrue(optionalUser.isPresent());
            User retrievedUser = optionalUser.get();
            assertEquals(user.getUserName(), retrievedUser.getUserName());
            assertEquals(user.getPassword(), retrievedUser.getPassword());
            assertEquals(user.getBalance(), retrievedUser.getBalance());
        }

        @Test
        @DisplayName("Тестирование метода получения юзера по его имени")
        void testGetByName() {
            User user = Utils.getUser();
            user = userRepository.addUser(user);
            Optional<User> optionalUser = userRepository.getByName(user.getUserName());
            assertTrue(optionalUser.isPresent());
            User retrievedUser = optionalUser.get();
            assertEquals(user.getUserName(), retrievedUser.getUserName());
            assertEquals(user.getPassword(), retrievedUser.getPassword());
            assertEquals(user.getBalance(), retrievedUser.getBalance());
        }
    }


