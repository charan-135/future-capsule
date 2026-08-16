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
import java.time.LocalDateTime;

@WebServlet("/edit-capsule")
public class EditCapsuleServlet extends HttpServlet {

    private TimeCapsuleService capsuleService;

    @Override
    public void init() {

        capsuleService =
                new TimeCapsuleService();
    }


    // ==========================================
    // OPEN EDIT PAGE
    // ==========================================

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
        // Get user
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
        // Ownership check
        // -----------------------------

        if (capsule.getUserId() != user.getId()) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You cannot edit this capsule."
            );

            return;
        }


        // -----------------------------
        // Status check
        // -----------------------------

        if (!"PENDING".equals(capsule.getStatus())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Delivered capsules cannot be edited."
            );

            return;
        }


        // -----------------------------
        // Send data to JSP
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
        // Open Edit UI
        // -----------------------------

        request.getRequestDispatcher(
                "/WEB-INF/views/edit-capsule.jsp"
        ).forward(
                request,
                response
        );
    }


    // ==========================================
    // UPDATE CAPSULE
    // ==========================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

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
        // Get user
        // -----------------------------

        User user =
                (User) session.getAttribute("user");


        String idParameter =
                request.getParameter("id");

        String title =
                request.getParameter("title");

        String message =
                request.getParameter("message");

        String deliveryDate =
                request.getParameter("deliveryDate");


        // -----------------------------
        // Validate parameters
        // -----------------------------

        if (idParameter == null ||
                title == null ||
                message == null ||
                deliveryDate == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Required information is missing."
            );

            return;
        }


        try {

            int capsuleId =
                    Integer.parseInt(idParameter);


            LocalDateTime dateTime =
                    LocalDateTime.parse(deliveryDate);


            // -----------------------------
            // Update capsule
            // -----------------------------

            boolean updated =
                    capsuleService.updateCapsule(
                            capsuleId,
                            user.getId(),
                            title,
                            message,
                            dateTime
                    );


            if (updated) {

                response.sendRedirect(
                        "my-capsules"
                );

            } else {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Capsule could not be updated."
                );
            }


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid capsule ID."
            );


        } catch (java.time.format.DateTimeParseException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid delivery date."
            );
        }
    }
}