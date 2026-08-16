package com.futurecapsule.service;

import com.futurecapsule.dao.UserDAO;
import com.futurecapsule.util.PasswordUtil;

public class RegistrationService {

    private final UserDAO userDAO;

    public RegistrationService() {

        this.userDAO =
                new UserDAO();
    }


    public boolean registerUser(
            String name,
            String email,
            String password) {

        // Basic validation

        if (name == null ||
                name.trim().isEmpty()) {

            return false;
        }

        if (email == null ||
                email.trim().isEmpty()) {

            return false;
        }

        if (password == null ||
                password.isEmpty()) {

            return false;
        }


        // Clean input

        name = name.trim();

        email = email.trim().toLowerCase();


        // Hash password before storing

        String hashedPassword =
                PasswordUtil.hashPassword(
                        password
                );


        // Create user and return
        // the actual database result

        return userDAO.createUser(
                name,
                email,
                hashedPassword
        );
    }
}