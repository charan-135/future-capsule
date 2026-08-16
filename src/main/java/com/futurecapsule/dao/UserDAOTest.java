package com.futurecapsule.dao;

import com.futurecapsule.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Test 1: Create a new user
        System.out.println("----- Creating User -----");

        userDAO.createUser(
                "Arjun",
                "arjun2@test.com",
                "MySecret123"
        );
        // Test 2: Find an existing user
        System.out.println("\n----- Finding User -----");

        User user = userDAO.findUserByEmail("rahul@test.com");

        if (user != null) {

            System.out.println("User found!");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());

        } else {

            System.out.println("User not found.");
        }
    }
}