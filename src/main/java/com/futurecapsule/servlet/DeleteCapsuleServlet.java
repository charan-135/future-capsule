package com.futurecapsule.servlet;

import com.futurecapsule.model.User;
import com.futurecapsule.service.TimeCapsuleService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/delete-capsule")
public class DeleteCapsuleServlet extends HttpServlet {

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
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            response.sendRedirect("login.html");

            return;
        }

        User user =
                (User) session.getAttribute("user");

        String idParameter =
                request.getParameter("id");

        if (idParameter == null) {

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

        boolean deleted =
                capsuleService.deleteCapsule(
                        capsuleId,
                        user.getId()
                );

        if (deleted) {

            response.sendRedirect(
                    "my-capsules"
            );

        } else {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Capsule could not be deleted."
            );
        }
    }
}