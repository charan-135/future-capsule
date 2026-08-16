package com.futurecapsule.servlet;

import com.futurecapsule.service.RegistrationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private RegistrationService registrationService;

    @Override
    public void init() {

        registrationService =
                new RegistrationService();
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        boolean registered =
                registrationService.registerUser(
                        name,
                        email,
                        password
                );


        // -----------------------------
        // REGISTRATION SUCCESS
        // -----------------------------

        if (registered) {

            response.sendRedirect(
                    "login.html?registered=true"
            );

            return;
        }


        // -----------------------------
        // REGISTRATION FAILED
        // -----------------------------

        response.sendRedirect(
                "register.html?error=failed"
        );
    }
}