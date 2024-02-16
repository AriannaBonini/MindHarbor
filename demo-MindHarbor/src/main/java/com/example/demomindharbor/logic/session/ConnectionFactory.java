package com.example.demomindharbor.logic.session;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConnectionFactory {
    private static Connection connection;

    private ConnectionFactory() {}

    static {
        try (InputStream input = new FileInputStream("src/main/java/com/example/demomindharbor/logic/dao/config.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("DB_URL");

            String user = properties.getProperty("USER");

            String pass = properties.getProperty("PASS");



            connection = DriverManager.getConnection(connectionUrl, user, pass);


        } catch (IOException | SQLException e) {

            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
    public static Connection getConnection() {

        return connection;
    }
}
