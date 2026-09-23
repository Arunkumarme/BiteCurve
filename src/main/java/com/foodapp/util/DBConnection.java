package com.foodapp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = getConfigValue("DB_URL", "db.url", "jdbc:mysql://localhost:3306/food_delivery_app");
    private static final String USER = getConfigValue("DB_USER", "db.user", "root");
    private static final String PASSWORD = getConfigValue("DB_PASSWORD", "db.password", "");

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB Connected Successfully ✅");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    private static String getConfigValue(String envName, String propertyName, String defaultValue) {
        String value = System.getenv(envName);
        if (value == null || value.isBlank()) {
            value = System.getProperty(propertyName);
        }
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
