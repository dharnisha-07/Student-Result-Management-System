package com.srms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static DBConnection instance;
    private final Properties properties = new Properties();

    private DBConnection() {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("FATAL: db.properties not found in classpath");
            }
            properties.load(input);
            String driver = properties.getProperty("db.driver");
            if (driver == null || driver.isBlank()) {
                throw new RuntimeException("FATAL: db.driver property missing in db.properties");
            }
            Class.forName(driver);
        } catch (IOException e) {
            throw new RuntimeException("FATAL: db.properties not found in classpath", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("FATAL: JDBC driver could not be loaded - " + e.getMessage(), e);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }

    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            boolean valid = connection != null && connection.isValid(2);
            LOGGER.log(Level.INFO, "DBConnection testConnection result: {0}", valid);
            return valid;
        } catch (SQLException e) {
            throw new RuntimeException("FATAL: Cannot connect to database - " + e.getMessage(), e);
        }
    }
}
