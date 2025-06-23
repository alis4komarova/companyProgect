package com.example.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/companykp";
    private static final String USER = "adminn";
    private static final String PASSWORD = "qwerty";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Загрузка драйвера
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC-драйвер не найден: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе: " + e.getMessage());
            return null;
        }
    }
}