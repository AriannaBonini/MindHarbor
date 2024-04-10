package com.example.mindharbor.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://Localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "paperino";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}