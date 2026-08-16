package com.futurecapsule.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.getConnection();

            System.out.println("Database connected successfully!");

            connection.close();

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}