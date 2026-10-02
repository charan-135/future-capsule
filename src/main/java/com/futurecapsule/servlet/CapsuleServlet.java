package com.futurecapsule.servlet;

import com.futurecapsule.model.User;
import com.futurecapsule.service.TimeCapsuleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.ZoneId;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/capsule")
public class CapsuleServlet extends HttpServlet {

    private TimeCapsuleService capsuleService;

    @Override
    public void init() {

        capsuleService =
                new TimeCapsuleService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // -----------------------------
            // Check logged-in user
            // -----------------------------

            User user =
                    (User) request.getSession()
                            .getAttribute("user");

            if (user == null) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                response.setContentType(
                        "text/plain;charset=UTF-8"
                );

                response.getWriter().print(
                        "LOGIN_REQUIRED"
                );

                return;
            }


            // -----------------------------
            // Get user ID
            // -----------------------------

            int userId =
                    user.getId();


            // -----------------------------
            // Get form data
            // -----------------------------

            String title =
                    request.getParameter("title");

            String message =
                    request.getParameter("message");

            String deliveryDate =
                    request.getParameter("deliveryDate");


            // -----------------------------
            // Validate delivery date
            // -----------------------------

            if (deliveryDate == null ||
                    deliveryDate.isBlank()) {

                throw new IllegalArgumentException(
                        "Delivery date is required."
                );
            }


            // -----------------------------
            // Convert date
            // -----------------------------

            LocalDateTime dateTime =
                    LocalDateTime.parse(deliveryDate)
                            .atZone(ZoneId.of("Asia/Kolkata"))
                            .withZoneSameInstant(ZoneId.of("UTC"))
                            .toLocalDateTime();


            // -----------------------------
            // Create capsule
            // -----------------------------

            int capsuleId =
                    capsuleService.createCapsule(
                            userId,
                            title,
                            message,
                            dateTime
                    );


            // -----------------------------
            // Return capsule ID
            // -----------------------------

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    capsuleId
            );


        } catch (IllegalArgumentException e) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    "ERROR:" +
                            e.getMessage()
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    "ERROR: "
                            + e.getClass()
                            .getSimpleName()
                            + " - "
                            + e.getMessage()
            );
        }
    }
}