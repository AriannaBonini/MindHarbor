package com.example.mindharbor.session;

import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.slf4j.Logger;

public class ConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);


    private static Connection connection;

    private ConnectionFactory() {}

    static {
        // Does not work if generating a jar file
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            if (input == null){
                logger.error("Impossibile recuperare le credenziali del DB");
            }
            properties.load(input);

            // get delle proprietà
            String jdbcURL = properties.getProperty("jdbcURL");
            String jdbcUsername = properties.getProperty("jdbcUsername");
            String jdbcPassword = properties.getProperty("jdbcPassword");

            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (IOException | SQLException e) {
            logger.error("Failed to establish a database connection", e);
        }
    }


    public static Connection getConnection() {
        return connection;
    }
}
