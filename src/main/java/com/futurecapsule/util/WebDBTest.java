package com.futurecapsule.util;

import java.sql.Connection;

public class WebDBTest {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {

            System.out.println("Database connection successful!");

        } catch (Exception e) {

            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}