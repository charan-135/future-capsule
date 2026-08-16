package com.futurecapsule.servlet;

import com.futurecapsule.model.TimeCapsule;
import com.futurecapsule.model.User;
import com.futurecapsule.service.TimeCapsuleService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private TimeCapsuleService capsuleService;

    @Override
    public void init() {
        capsuleService = new TimeCapsuleService();
    }

    @Override
    protected void doGet(
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

        User user =
                (User) session.getAttribute("user");

        // -----------------------------
        // Get user's capsules
        // -----------------------------

        List<TimeCapsule> capsules =
                capsuleService.getCapsulesByUser(
                        user.getId()
                );

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        var out = response.getWriter();

        // -----------------------------
        // HTML
        // -----------------------------

        out.println("""
                <!DOCTYPE html>
                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width,
                                   initial-scale=1.0">

                    <title>Future Capsule</title>

                    <link rel="stylesheet"
                          href="css/style.css">

                </head>

                <body>

                <div class="container">

                    <!-- NAVIGATION -->

                    <nav class="navbar">

                        <a href="dashboard"
                           class="logo">
                            FUTURE
                            <span>CAPSULE</span>
                        </a>

                        <div class="nav-links">

                            <a href="my-capsules">
                                My Capsules
                            </a>

                            <a href="logout"
                               class="nav-menu">
                                Exit
                            </a>

                        </div>

                    </nav>


                    <!-- HERO -->

                    <section class="hero">

                        <div class="hero-label">
                            YOUR PERSONAL TIME MACHINE
                        </div>

                        <h1>
                            Write for
                            <br>
                            someone
                            <br>
                            <em>you haven't</em>
                            <br>
                            become yet.
                        </h1>

                        <div class="hero-orb"></div>

                        <p class="hero-description">
                            Your words stay here until
                            time catches up with them.
                            Write something today and let
                            your future self discover it
                            when the moment arrives.
                        </p>

                        <div class="actions">

                            <a href="create-capsule.html"
                               class="btn btn-primary">
                                + Create Capsule
                            </a>

                            <a href="my-capsules"
                               class="btn btn-outline">
                                View Capsules →
                            </a>

                        </div>

                    </section>


                    <!-- STATS -->

                    <section class="stats">

                        <div class="stat">

                            <div class="stat-label">
                                Total Capsules
                            </div>

                            <div class="stat-value">
                """);

        out.println(capsules.size());

        out.println("""
                            </div>

                        </div>


                        <div class="stat">

                            <div class="stat-label">
                                Status
                            </div>

                            <div class="stat-value">
                                ACTIVE
                            </div>

                        </div>


                        <div class="stat">

                            <div class="stat-label">
                                Future Deliveries
                            </div>

                            <div class="stat-value">
                                WAITING
                            </div>

                        </div>

                    </section>


                    <!-- CAPSULE PREVIEW -->

                    <section class="section">

                        <div class="section-heading">

                            <div>

                                <div class="section-number">
                                    01 / ARCHIVE
                                </div>

                                <h2>
                                    Messages
                                    <br>
                                    for tomorrow.
                                </h2>

                            </div>

                            <a href="my-capsules"
                               class="btn btn-outline">
                                View all →
                            </a>

                        </div>


                        <div class="capsule-grid">

                """);

        // -----------------------------
        // Show latest capsules
        // -----------------------------

        int limit =
                Math.min(capsules.size(), 4);

        for (int i = 0; i < limit; i++) {

            TimeCapsule capsule =
                    capsules.get(i);

            out.println("""
                    <article class="capsule-card">

                        <div>

                            <div class="capsule-index">
                """);

            out.println(
                    String.format(
                            "%02d",
                            i + 1
                    )
            );

            out.println("""
                            </div>

                        </div>


                        <div>

                            <div class="capsule-date">
                """);

            out.println(
                    capsule.getDeliveryDate()
            );

            out.println("""
                            </div>

                            <br>

                            <h3>
                """);

            out.println(
                    escapeHtml(
                            capsule.getTitle()
                    )
            );

            out.println("""
                            </h3>

                            <p class="capsule-message">
                                "
                """);

            String message =
                    capsule.getMessage();

            if (message.length() > 120) {

                message =
                        message.substring(0, 120)
                                + "...";
            }

            out.println(
                    escapeHtml(message)
            );

            out.println("""
                                "
                            </p>

                        </div>


                        <div class="capsule-footer">

                            <span class="status">
                """);

            out.println(
                    capsule.getStatus()
            );

            out.println("""
                            </span>

                            <a
                                href="view-capsule?id=
                """);

            out.println(
                    capsule.getId()
            );

            out.println("""
                                "
                                class="btn btn-outline">
                                Open →
                            </a>

                        </div>

                    </article>
                """);
        }

        // -----------------------------
        // No capsules
        // -----------------------------

        if (capsules.isEmpty()) {

            out.println("""
                    <div class="info-card">

                        <h3>
                            Your archive is empty.
                        </h3>

                        <p>
                            Your first message to the
                            future is waiting to be written.
                            Create a capsule and start your
                            journey through time.
                        </p>

                        <div class="actions">

                            <a
                                href="create-capsule.html"
                                class="btn btn-primary">
                                Create First Capsule
                            </a>

                        </div>

                    </div>
            """);
        }

        out.println("""
                        </div>

                    </section>


                    <!-- FOOTER -->

                    <footer class="footer">

                        <span>
                            FUTURE CAPSULE
                        </span>

                        <span>
                            Written today ·
                            Delivered tomorrow
                        </span>

                    </footer>

                </div>

                </body>

                </html>
                """);
    }


    // -----------------------------------
    // Basic HTML escaping
    // -----------------------------------

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}