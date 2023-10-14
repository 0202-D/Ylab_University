package io.ylab.repository;

import io.ylab.Utils;
import io.ylab.dao.transaction.JdbcTransactionRepository;
import io.ylab.model.Transaction;
import io.ylab.model.User;
import io.ylab.utils.DataBaseConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestContainersTransactionRepositoryTest {
    private PostgreSQLContainer<?> container;
    private Connection connection;
    private JdbcTransactionRepository transactionRepository;
    private final static String CREATE_SCHEMA_IF_NOT_EXISTS_DOMAIN = "CREATE SCHEMA IF NOT EXISTS domain";
    private final static String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS domain.user" +
            " (user_id SERIAL PRIMARY KEY, user_name VARCHAR(255),password varchar(255),balance decimal)";
    private final static String CREATE_TABLE_QUERY_TWO = "CREATE TABLE IF NOT EXISTS domain.transaction" +
            " (transaction_id SERIAL PRIMARY KEY, transactional_type VARCHAR(255),sum decimal,user_id bigint)";
    private final static String INSERT = "INSERT INTO domain.user (user_name,password,balance) VALUES(?,?,?)";

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
        transactionRepository = new JdbcTransactionRepository();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        connection.close();
        container.stop();
    }

    @Test
    void testAddTransaction() {
        User user = Utils.getUser();
        Transaction transaction = Utils.getDebitTransaction();

        transactionRepository.addTransaction(transaction);

        List<Transaction> transactions = transactionRepository.getAllByUserName(user.getUserName());
        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.get(0));
    }

    @Test
    void testGetAllByUserName() {
        User user = Utils.getUser();
        Transaction transaction1 = Utils.getDebitTransaction();
        Transaction transaction2 = Utils.getCreditTransaction();

        transactionRepository.addTransaction(transaction1);
        transactionRepository.addTransaction(transaction2);

        List<Transaction> transactions = transactionRepository.getAllByUserName(user.getUserName());
        assertEquals(2, transactions.size());
    }

    @Test
    void testGetById() {
        Transaction transaction = Utils.getDebitTransaction();

        transactionRepository.addTransaction(transaction);

        Optional<Transaction> optionalTransaction = transactionRepository.getById(transaction.getTransactionId());
        assertTrue(optionalTransaction.isPresent());
        assertEquals(transaction, optionalTransaction.get());
    }
}

