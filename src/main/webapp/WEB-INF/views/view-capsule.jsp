<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.futurecapsule.model.TimeCapsule" %>
<%@ page import="com.futurecapsule.model.User" %>

<%
    TimeCapsule capsule =
            (TimeCapsule) request.getAttribute("capsule");

    User user =
            (User) request.getAttribute("user");

    boolean delivered =
            "DELIVERED".equals(capsule.getStatus());
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0"
    >

    <title>
        <%= capsule.getTitle() %> — Future Capsule
    </title>


    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }


        body {

            min-height: 100vh;

            background: #090909;

            color: #f4f1eb;

            font-family:
                    Arial,
                    Helvetica,
                    sans-serif;

            padding: 0 7vw 80px;

        }


        /* =========================
           NAVIGATION
        ========================= */

        nav {

            height: 90px;

            display: flex;

            align-items: center;

            justify-content: space-between;

            border-bottom:
                    1px solid #252525;

        }


        .logo {

            color: white;

            text-decoration: none;

            font-size: 18px;

            font-weight: 600;

            letter-spacing: -0.03em;

        }


        .nav-right {

            display: flex;

            align-items: center;

            gap: 28px;

        }


        .nav-user {
            color: #999;
            font-size: 13px;
        }


       .nav-link {
           color: #999;
           text-decoration: none;
           font-size: 11px;

            letter-spacing: 0.14em;

            text-transform: uppercase;

        }


        .nav-link:hover {

            color: white;

        }


        /* =========================
           MAIN
        ========================= */

        main {

            max-width: 900px;

            margin: 90px auto 0;

        }


        .eyebrow {

            display: flex;

            align-items: center;

            gap: 10px;

            color: #999;

            font-family: monospace;

            font-size: 10px;

            letter-spacing: 0.2em;

            text-transform: uppercase;

            margin-bottom: 30px;

        }


        .status-dot {

            width: 7px;

            height: 7px;

            border-radius: 50%;

            background: #ff6b45;

        }


        .status-dot.delivered {

            background: #999;

        }


        /* =========================
           TITLE
        ========================= */

        h1 {

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: clamp(
                    52px,
                    8vw,
                    100px
            );

            font-weight: 400;

            line-height: 0.95;

            letter-spacing: -0.065em;

            max-width: 850px;

        }


        h1 em {

            color: #ff6b45;

            font-style: italic;

        }


        /* =========================
           MESSAGE
        ========================= */

        .message-section {

            margin-top: 80px;

            padding-top: 55px;

            border-top:
                    1px solid #252525;

        }


        .message-label {

            color: #888;

            font-family: monospace;

            font-size: 10px;

            letter-spacing: 0.18em;

            text-transform: uppercase;

            margin-bottom: 30px;

        }


        .message {
            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: clamp(
                    25px,
                    3vw,
                    38px
            );

            font-weight: 400;

            line-height: 1.45;

            color: #e4e1db;

            white-space: pre-wrap;

            max-width: 800px;
        }


        /* Automatically delivered message */

        .message-section.delivered-message .message {
            max-width: 850px;

            margin-left: auto;
            margin-right: auto;

            text-align: center;
        }

            font-weight: 400;

            line-height: 1.45;

            color: #ddd;

            white-space: pre-wrap;

            max-width: 800px;

        }

        .message-section.delivered-message .message {
            max-width: 850px;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
        }


        /* =========================
           SEALED MESSAGE
        ========================= */

        .locked-message {

            min-height: 260px;

            display: flex;

            flex-direction: column;

            align-items: center;

            justify-content: center;

            border:
                    1px solid #252525;

            background: #0d0d0d;

            text-align: center;

            padding: 50px 30px;

        }


        .lock {

            width: 55px;

            height: 55px;

            display: flex;

            align-items: center;

            justify-content: center;

            border:
                    1px solid #333;

            border-radius: 50%;

            font-size: 22px;

            margin-bottom: 25px;

        }


        .locked-message h2 {

            font-family:
                    Georgia,
                    serif;

            font-size: 30px;

            font-weight: 400;

            margin-bottom: 12px;

        }


        .locked-message p {

            max-width: 430px;

            color: #666;

            font-size: 13px;

            line-height: 1.7;

        }


        /* =========================
           INFO
        ========================= */

        .capsule-info {

            margin-top: 70px;

            display: grid;

            grid-template-columns:
                    repeat(2, 1fr);

            border-top:
                    1px solid #252525;

            border-bottom:
                    1px solid #252525;

        }


        .info-item {

            padding: 30px 0;

        }


        .info-item:nth-child(2) {

            padding-left: 45px;

            border-left:
                    1px solid #252525;

        }


        .info-label {

            display: block;

            color: #888;

            font-family: monospace;

            font-size: 10px;

            letter-spacing: 0.18em;

            text-transform: uppercase;

            margin-bottom: 12px;

        }


        .info-value {

            color: #ddd;

            font-family: monospace;

            font-size: 12px;

        }


        /* =========================
           STATUS BANNER
        ========================= */

        .delivery-status {

            margin-top: 50px;

            padding: 22px 25px;

            border:
                    1px solid #252525;

            display: flex;

            align-items: center;

            justify-content: space-between;

        }


        .delivery-status span {

            color: #888;

            font-family: monospace;

            font-size: 10px;

            letter-spacing: 0.15em;

            text-transform: uppercase;

        }


        .delivery-status strong {

            color: #ff6b45;

            font-family: monospace;

            font-size: 10px;

            letter-spacing: 0.12em;

            text-transform: uppercase;

        }


        .delivery-status.delivered strong {

            color: #ddd;

        }


        /* =========================
           ACTIONS
        ========================= */

        .actions {

            margin-top: 55px;

            display: flex;

            align-items: center;

            justify-content: space-between;

        }


        .back {

            color: #999;

            text-decoration: none;

            font-family: monospace;

            font-size: 11px;

            letter-spacing: 0.15em;

            text-transform: uppercase;

        }


        .back:hover {

            color: white;

        }


        .edit {

            padding: 14px 22px;

            border-radius: 100px;

            background: #ff6b45;

            color: #080808;

            text-decoration: none;

            font-size: 10px;

            font-weight: 700;

            letter-spacing: 0.1em;

            text-transform: uppercase;

        }


        .edit:hover {

            background: #ff815f;

        }


        /* =========================
           RESPONSIVE
        ========================= */

        @media (max-width: 650px) {

            body {

                padding: 0 5vw 60px;

            }


            nav {

                height: 75px;

            }


            .nav-user {

                display: none;

            }


            main {

                margin-top: 60px;

            }


            .message-section {

                margin-top: 55px;

                padding-top: 40px;

            }


            .capsule-info {

                grid-template-columns: 1fr;

            }


            .info-item:nth-child(2) {

                padding-left: 0;

                border-left: none;

                border-top:
                        1px solid #252525;

            }


            .delivery-status {

                flex-direction: column;

                align-items: flex-start;

                gap: 12px;

            }


            .actions {

                flex-direction: column;

                align-items: flex-start;

                gap: 25px;

            }

        }

    </style>

</head>


<body>


<!-- =========================
     NAVIGATION
========================= -->

<nav>

    <a
            href="my-capsules"
            class="logo">
        Future Capsule
    </a>


    <div class="nav-right">

        <span class="nav-user">

            Welcome,
            <%= user.getName() %>

        </span>


        <a
                href="my-capsules"
                class="nav-link">
            My Capsules
        </a>

    </div>

</nav>


<!-- =========================
     CONTENT
========================= -->

<main>


    <!-- STATUS -->

    <div class="eyebrow">

        <span
                class="status-dot
                <%= delivered ? "delivered" : "" %>">
        </span>

        <%= delivered
                ? "Capsule Delivered"
                : "Capsule Sealed" %>

    </div>


    <!-- TITLE -->

    <h1>

        <%= capsule.getTitle() %>

    </h1>


    <!-- MESSAGE -->

   <section class="message-section <%= delivered ? "delivered-message" : "" %>">

        <div class="message-label">

            <%= delivered
                    ? "Your Message"
                    : "Message Sealed" %>

        </div>


        <% if (delivered) { %>


            <div class="message">

                <%= capsule.getMessage() %>

            </div>


        <% } else { %>


            <div class="locked-message">

                <div class="lock">
                    🔒
                </div>


                <h2>
                    Your message is sealed.
                </h2>


                <p>

                    This message is safely stored
                    and will be revealed when
                    its delivery date arrives.

                </p>

            </div>


        <% } %>

    </section>


    <!-- CAPSULE INFORMATION -->

    <section class="capsule-info">


        <div class="info-item">

            <span class="info-label">

                Sealed On

            </span>


            <span class="info-value">

                <%= capsule.getCreatedAt() %>

            </span>

        </div>


        <div class="info-item">

            <span class="info-label">

                <%= delivered
                        ? "Delivered On"
                        : "Opens On" %>

            </span>


            <span class="info-value">

                <%= capsule.getDeliveryDate() %>

            </span>

        </div>


    </section>


    <!-- STATUS -->

    <div class="delivery-status
            <%= delivered ? "delivered" : "" %>">

        <span>
            Current Status
        </span>


        <strong>

            <%= capsule.getStatus() %>

        </strong>

    </div>


    <!-- ACTIONS -->

    <div class="actions">


        <a
                href="my-capsules"
                class="back">
            ← Back to My Capsules
        </a>


        <% if (!delivered) { %>


            <a
                    href="edit-capsule?id=<%= capsule.getId() %>"
                    class="edit">
                Edit Capsule
            </a>


        <% } %>


    </div>


</main>


</body>

</html>