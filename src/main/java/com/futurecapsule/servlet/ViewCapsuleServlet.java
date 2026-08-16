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

@WebServlet("/view-capsule")
public class ViewCapsuleServlet extends HttpServlet {

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
        // Check login
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
        // Get capsule ID
        // -----------------------------

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
                idParameter.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Capsule ID is required."
            );

            return;
        }


        int capsuleId;

        try {

            capsuleId =
                    Integer.parseInt(idParameter);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid capsule ID."
            );

            return;
        }


        // -----------------------------
        // Find capsule
        // -----------------------------

        TimeCapsule capsule =
                capsuleService.getCapsule(capsuleId);

        if (capsule == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Capsule not found."
            );

            return;
        }


        // -----------------------------
        // Security check
        // -----------------------------

        if (capsule.getUserId() != user.getId()) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You cannot access this capsule."
            );

            return;
        }


        // -----------------------------
        // Send capsule to JSP
        // -----------------------------

        request.setAttribute(
                "capsule",
                capsule
        );

        request.setAttribute(
                "user",
                user
        );


        // -----------------------------
        // Open View Capsule UI
        // -----------------------------

        request.getRequestDispatcher(
                "/WEB-INF/views/view-capsule.jsp"
        ).forward(
                request,
                response
        );
    }
}