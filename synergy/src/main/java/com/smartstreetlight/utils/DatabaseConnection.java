package com.smartstreetlight.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/smart_light?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = "root"; // Replace with your MySQL password

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish Connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
