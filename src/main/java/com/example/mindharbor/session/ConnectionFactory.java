package com.example.mindharbor.session;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            System.out.println(jdbcURL);
            System.out.println(jdbcUsername);
            System.out.println(jdbcPassword);

            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

        } catch (IOException | SQLException e) {
            logger.error("Tentativo di connessione al database fallito", e);
        }
    }


    public static Connection getConnection() {
        return connection;
    }
}
