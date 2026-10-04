package com.futurecapsule.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found.",
                    e
            );
        }

        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        String url =
                "jdbc:mysql://" + host + ":" + port + "/" + database
                        + "?useSSL=false"
                        + "&allowPublicKeyRetrieval=true"
                        + "&serverTimezone=Asia/Kolkata";

        Connection connection =
                DriverManager.getConnection(
                        url,
                        user,
                        password
                );

        // Make MySQL's session clock explicitly IST. This keeps
        // TIMESTAMP/CURRENT_TIMESTAMP values consistent with the
        // application's Asia/Kolkata wall-clock time.
        try (Statement statement =
                     connection.createStatement()) {
            statement.execute("SET time_zone = '+05:30'");
        }

        return connection;
    }
}