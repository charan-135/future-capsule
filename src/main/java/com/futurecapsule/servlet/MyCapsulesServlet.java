package com.futurecapsule.servlet;

import com.futurecapsule.model.TimeCapsule;
import com.futurecapsule.model.User;
import com.futurecapsule.service.TimeCapsuleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-capsules")
public class MyCapsulesServlet extends HttpServlet {

    private TimeCapsuleService capsuleService;

    @Override
    public void init() {

        capsuleService =
                new TimeCapsuleService();
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // -----------------------------
        // Check logged-in user
        // -----------------------------

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.sendRedirect("login.html");

            return;
        }


        // -----------------------------
        // Get logged-in user
        // -----------------------------

        User user =
                (User) session.getAttribute("user");


        // -----------------------------
        // Get user's capsules
        // -----------------------------

        List<TimeCapsule> capsules =
                capsuleService.getCapsulesByUser(
                        user.getId()
                );


        // -----------------------------
        // Send data to JSP
        // -----------------------------

        request.setAttribute(
                "user",
                user
        );

        request.setAttribute(
                "capsules",
                capsules
        );


        // -----------------------------
        // Open My Capsules UI
        // -----------------------------

        request.getRequestDispatcher(
                "/WEB-INF/views/my-capsules.jsp"
        ).forward(
                request,
                response
        );
    }
}