package com.futurecapsule.service;

import com.futurecapsule.dao.UserDAO;
import com.futurecapsule.model.User;
import com.futurecapsule.util.PasswordUtil;

public class AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }

    public User login(String email, String password) {

        // Find the user using email
        User user = userDAO.findUserByEmail(email);

        // User does not exist
        if (user == null) {
            return null;
        }

        // Check entered password against stored BCrypt hash
        boolean passwordMatches =
                PasswordUtil.checkPassword(
                        password,
                        user.getPassword()
                );

        if (passwordMatches) {
            return user;
        }

        return null;
    }
}