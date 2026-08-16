<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.futurecapsule.model.TimeCapsule" %>
<%@ page import="com.futurecapsule.model.User" %>

<%
    TimeCapsule capsule =
            (TimeCapsule) request.getAttribute("capsule");

    User user =
            (User) request.getAttribute("user");

    String deliveryDate =
            capsule.getDeliveryDate()
                    .toString()
                    .substring(0, 16);
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0"
    >

    <title>Edit Capsule — Future Capsule</title>


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

            color: #fff;

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

            color: #777;

            font-size: 12px;

        }


        .nav-link {

            color: #777;

            text-decoration: none;

            font-size: 10px;

            letter-spacing: 0.14em;

            text-transform: uppercase;

        }


        .nav-link:hover {

            color: #fff;

        }


        /* =========================
           MAIN
        ========================= */

        main {

            max-width: 850px;

            margin: 85px auto 0;

        }


        .eyebrow {

            color: #777;

            font-family: monospace;

            font-size: 9px;

            letter-spacing: 0.2em;

            text-transform: uppercase;

            margin-bottom: 20px;

        }


        h1 {

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: clamp(
                    50px,
                    7vw,
                    90px
            );

            font-weight: 400;

            line-height: 0.95;

            letter-spacing: -0.06em;

        }


        h1 em {

            color: #ff6b45;

            font-style: italic;

        }


        .intro {

            max-width: 520px;

            margin-top: 25px;

            color: #666;

            font-size: 13px;

            line-height: 1.7;

        }


        /* =========================
           FORM
        ========================= */

        .form-container {

            margin-top: 65px;

            padding-top: 45px;

            border-top:
                    1px solid #252525;

        }


        .field {

            margin-bottom: 35px;

        }


        .field-header {

            display: flex;

            justify-content: space-between;

            align-items: center;

            margin-bottom: 12px;

        }


        label {

            color: #777;

            font-family: monospace;

            font-size: 9px;

            letter-spacing: 0.18em;

            text-transform: uppercase;

        }


        .counter {

            color: #444;

            font-family: monospace;

            font-size: 9px;

        }


        input,
        textarea {

            width: 100%;

            border: 0;

            border-bottom:
                    1px solid #303030;

            outline: none;

            background: transparent;

            color: #eee;

            font-family:
                    Arial,
                    Helvetica,
                    sans-serif;

            transition:
                    border-color 0.2s ease;

        }


        input {

            height: 55px;

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: 26px;

            letter-spacing: -0.02em;

        }


        textarea {

            min-height: 180px;

            resize: vertical;

            padding: 15px 0;

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: 19px;

            line-height: 1.6;

        }


        input:focus,
        textarea:focus {

            border-bottom-color:
                    #ff6b45;

        }


        input::placeholder,
        textarea::placeholder {

            color: #333;

        }


        /* =========================
           DATE / TIME
        ========================= */

        .datetime {

            display: grid;

            grid-template-columns:
                    1fr 1fr;

            gap: 25px;

        }


        input[type="date"],
        input[type="time"] {

            font-family: monospace;

            font-size: 14px;

            color: #ccc;

            color-scheme: dark;

        }


        /* =========================
           WARNING
        ========================= */

        .notice {

            margin-top: 15px;

            padding: 18px 20px;

            border:
                    1px solid #242424;

            background: #0d0d0d;

            color: #666;

            font-size: 11px;

            line-height: 1.6;

        }


        .notice strong {

            color: #999;

        }


        /* =========================
           ACTIONS
        ========================= */

        .actions {

            margin-top: 50px;

            display: flex;

            align-items: center;

            justify-content: space-between;

        }


        .back {

            color: #777;

            text-decoration: none;

            font-family: monospace;

            font-size: 9px;

            letter-spacing: 0.15em;

            text-transform: uppercase;

        }


        .back:hover {

            color: #fff;

        }


        .update-button {

            border: 0;

            border-radius: 100px;

            padding: 16px 28px;

            background: #ff6b45;

            color: #080808;

            font-size: 10px;

            font-weight: 700;

            letter-spacing: 0.12em;

            text-transform: uppercase;

            cursor: pointer;

            transition:
                    transform 0.2s ease,
                    background 0.2s ease;

        }


        .update-button:hover {

            background: #ff815f;

            transform: translateY(-2px);

        }


        .update-button:disabled {

            opacity: 0.5;

            cursor: not-allowed;

            transform: none;

        }


        /* =========================
           RESPONSIVE
        ========================= */

        @media (max-width: 600px) {

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


            .datetime {

                grid-template-columns: 1fr;

                gap: 30px;

            }


            .actions {

                flex-direction: column;

                align-items: flex-start;

                gap: 25px;

            }


            input {

                font-size: 22px;

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
     MAIN
========================= -->

<main>


    <div class="eyebrow">
        Edit your sealed message
    </div>


    <h1>
        Refine your <em>capsule.</em>
    </h1>


    <p class="intro">

        Make any changes you'd like before
        your message travels into the future.
        Once delivered, the capsule becomes
        permanently sealed.

    </p>


    <!-- =========================
         FORM
    ========================= -->

    <form
            id="editForm"
            class="form-container"
            action="edit-capsule"
            method="post">


        <input
                type="hidden"
                name="id"
                value="<%= capsule.getId() %>"
        >


        <!-- TITLE -->

        <div class="field">

            <div class="field-header">

                <label for="title">
                    Title
                </label>

                <span
                        class="counter"
                        id="titleCounter">
                    0 / 120
                </span>

            </div>


            <input
                    type="text"
                    id="title"
                    name="title"
                    maxlength="120"
                    value="<%= capsule.getTitle() %>"
                    placeholder="A message for future me..."
                    required
            >

        </div>


        <!-- MESSAGE -->

        <div class="field">

            <div class="field-header">

                <label for="message">
                    Message
                </label>

                <span
                        class="counter"
                        id="messageCounter">
                    0 / 2000
                </span>

            </div>


            <textarea
                    id="message"
                    name="message"
                    maxlength="2000"
                    placeholder="Write something worth remembering..."
                    required
            ><%= capsule.getMessage() %></textarea>

        </div>


        <!-- DELIVERY -->

        <div class="field">

            <div class="field-header">

                <label>
                    Delivery Date & Time
                </label>

            </div>


            <div class="datetime">

                <input
                        type="date"
                        id="deliveryDate"
                        required
                >


                <input
                        type="time"
                        id="deliveryTime"
                        required
                >

            </div>


            <input
                    type="hidden"
                    id="combinedDeliveryDate"
                    name="deliveryDate"
            >


            <div class="notice">

                <strong>Important:</strong>
                The capsule must remain scheduled
                for a future date. Once the delivery
                time arrives, the message becomes
                permanently delivered.

            </div>

        </div>


        <!-- ACTIONS -->

        <div class="actions">

            <a
                    href="my-capsules"
                    class="back">
                ← Cancel
            </a>


            <button
                    type="submit"
                    class="update-button"
                    id="updateButton">

                Save Changes

            </button>

        </div>


    </form>


</main>


<script>

    const title =
        document.getElementById("title");

    const message =
        document.getElementById("message");

    const titleCounter =
        document.getElementById("titleCounter");

    const messageCounter =
        document.getElementById("messageCounter");


    function updateCounters() {

        titleCounter.textContent =
                title.value.length + " / 120";

        messageCounter.textContent =
                message.value.length + " / 2000";
    }


    title.addEventListener(
            "input",
            updateCounters
    );


    message.addEventListener(
            "input",
            updateCounters
    );


    updateCounters();


    /*
     * Existing delivery date
     */

    const existingDateTime =
            "<%= deliveryDate %>";


    const existingDate =
            existingDateTime.substring(
                    0,
                    10
            );


    const existingTime =
            existingDateTime.substring(
                    11,
                    16
            );


    document.getElementById(
            "deliveryDate"
    ).value = existingDate;


    document.getElementById(
            "deliveryTime"
    ).value = existingTime;


    /*
     * Submit
     */

    document.getElementById(
            "editForm"
    ).addEventListener(
            "submit",
            function (event) {

                const date =
                        document.getElementById(
                                "deliveryDate"
                        ).value;

                const time =
                        document.getElementById(
                                "deliveryTime"
                        ).value;


                if (!date || !time) {

                    event.preventDefault();

                    alert(
                            "Please select a delivery date and time."
                    );

                    return;
                }


                document.getElementById(
                        "combinedDeliveryDate"
                ).value =
                        date + "T" + time;


                const button =
                        document.getElementById(
                                "updateButton"
                        );


                button.disabled = true;

                button.textContent =
                        "SAVING...";

            }
    );

</script>


</body>

</html>