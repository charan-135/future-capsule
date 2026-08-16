package com.futurecapsule.service;

import com.futurecapsule.model.User;

public class AuthenticationTest {

    public static void main(String[] args) {

        AuthenticationService authenticationService =
                new AuthenticationService();

        System.out.println("----- Correct Password Test -----");

        User user = authenticationService.login(
                "arjun2@test.com",
                "MySecret123"
        );

        if (user != null) {

            System.out.println("Login successful!");
            System.out.println("Welcome, " + user.getName());

        } else {

            System.out.println("Login failed!");
        }


        System.out.println("\n----- Wrong Password Test -----");

        User wrongUser = authenticationService.login(
                "arjun2@test.com",
                "WrongPassword"
        );

        if (wrongUser != null) {

            System.out.println("Login successful!");

        } else {

            System.out.println("Login failed!");
        }
    }
}