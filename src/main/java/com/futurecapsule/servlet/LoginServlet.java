package com.futurecapsule.servlet;

import com.futurecapsule.model.User;
import com.futurecapsule.service.AuthenticationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthenticationService authenticationService;

    @Override
    public void init() {

        authenticationService =
                new AuthenticationService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        // Basic validation
        if (email == null ||
                password == null ||
                email.trim().isEmpty() ||
                password.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?error=invalid"
            );

            return;
        }


        User user =
                authenticationService.login(
                        email.trim(),
                        password
                );


        // =========================
        // LOGIN SUCCESS
        // =========================

        if (user != null) {

            request.getSession(true)
                    .setAttribute(
                            "user",
                            user
                    );

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard"
            );

            return;
        }


        // =========================
        // LOGIN FAILED
        // =========================

        response.sendRedirect(
                request.getContextPath()
                        + "/login.html?error=invalid"
        );
    }
}