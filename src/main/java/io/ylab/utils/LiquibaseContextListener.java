package io.ylab.utils;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LiquibaseContextListener implements ServletContextListener {
    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        Class.forName("org.postgresql.Driver");
        Properties liquibaseProps = new Properties();
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("liquibase.properties")) {
            liquibaseProps.load(inputStream);
        }
        String url = liquibaseProps.getProperty("url");
        String username = liquibaseProps.getProperty("username");
        String password = liquibaseProps.getProperty("password");

        Connection connection = DriverManager.getConnection(url, username, password);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(),
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection));
        liquibase.update(new Contexts(),new LabelExpression());
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
