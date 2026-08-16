<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.futurecapsule.model.TimeCapsule" %>
<%@ page import="com.futurecapsule.model.User" %>

<%
    User user = (User) request.getAttribute("user");

    List<TimeCapsule> capsules =
            (List<TimeCapsule>) request.getAttribute("capsules");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0"
    >

    <title>My Capsules — Future Capsule</title>


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


        /* -------------------------
           NAVIGATION
        ------------------------- */

        nav {

            height: 90px;

            display: flex;

            align-items: center;

            justify-content: space-between;

            border-bottom:
                    1px solid #252525;

        }


        .logo {

            color: #ffffff;

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


        .logout {

            color: #aaa;

            text-decoration: none;

            font-size: 11px;

            letter-spacing: 0.12em;

            text-transform: uppercase;

        }


        .logout:hover {

            color: #ffffff;

        }


        /* -------------------------
           HEADER
        ------------------------- */

        .page-header {

            max-width: 1100px;

            margin: 90px auto 55px;

        }


        .eyebrow {

            color: #777;

            font-size: 10px;

            letter-spacing: 0.2em;

            text-transform: uppercase;

            margin-bottom: 18px;

        }


        h1 {

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: clamp(
                    48px,
                    7vw,
                    92px
            );

            font-weight: 400;

            letter-spacing: -0.06em;

            line-height: 0.95;

        }


        h1 em {

            color: #ff6b45;

            font-style: italic;

        }


        .subtitle {

            margin-top: 25px;

            max-width: 500px;

            color: #777;

            font-size: 14px;

            line-height: 1.7;

        }


        .header-actions {

            margin-top: 35px;

        }


        .create-button {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            padding: 15px 25px;

            background: #ff6b45;

            color: #080808;

            text-decoration: none;

            font-size: 11px;

            font-weight: 700;

            letter-spacing: 0.1em;

            text-transform: uppercase;

            border-radius: 100px;

            transition:
                    transform 0.2s ease,
                    background 0.2s ease;

        }


        .create-button:hover {

            transform: translateY(-2px);

            background: #ff815f;

        }


        /* -------------------------
           CAPSULE GRID
        ------------------------- */

      .capsules {
          max-width: 1100px;

          margin: auto;

          display: grid;

          grid-template-columns:
              repeat(2, minmax(0, 1fr));

          gap: 24px;

          align-items: stretch;
      }


       .capsule-card {

           position: relative;

           height: 390px;
           min-height: 390px;
           max-height: 390px;

           padding: 32px;

           border:
               1px solid #252525;

           border-radius: 12px;

           background: #0e0e0e;

           display: flex;

           flex-direction: column;

           transition:
               border-color 0.25s ease,
               transform 0.25s ease,
               background 0.25s ease,
               box-shadow 0.25s ease;
       }

        .capsule-card:hover {

            border-color: #444;

            background: #111111;

            transform: translateY(-5px);

            box-shadow:
                0 18px 45px rgba(0, 0, 0, 0.28);

        }


        /* -------------------------
           CARD TOP
        ------------------------- */

        .card-top {

            display: flex;

            align-items: center;

            justify-content: space-between;

            margin-bottom: 45px;

        }


        .status {

            display: inline-flex;

            align-items: center;

            gap: 8px;

            font-size: 9px;

            letter-spacing: 0.16em;

            text-transform: uppercase;

        }


        .status-dot {

            width: 6px;

            height: 6px;

            border-radius: 50%;

            background: #ff6b45;

        }


        .status.delivered .status-dot {

            background: #8a8a8a;

        }


        .status.pending {

            color: #ff8b6b;

        }


        .status.delivered {

            color: #999;

        }


        .card-number {

            color: #444;

            font-family:
                    monospace;

            font-size: 10px;

        }


        /* -------------------------
           CARD CONTENT
        ------------------------- */

        .capsule-title {

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: 31px;

            font-weight: 400;

            line-height: 1.05;

            letter-spacing: -0.04em;

            margin-bottom: 18px;

        }


        .capsule-message {

            color: #777;

            font-size: 13px;

            line-height: 1.7;

            max-width: 500px;

            display: -webkit-box;

            -webkit-line-clamp: 3;

            -webkit-box-orient: vertical;

            overflow: hidden;

        }


        /* -------------------------
           DATE
        ------------------------- */

        .card-date {

            margin-top: auto;

            padding-top: 35px;

            border-top:
                    1px solid #222;

        }


        .date-label {

            color: #555;

            font-size: 8px;

            letter-spacing: 0.18em;

            text-transform: uppercase;

            margin-bottom: 8px;

        }


        .date-value {

            color: #ddd;

            font-family:
                    monospace;

            font-size: 12px;

        }


        /* -------------------------
           ACTIONS
        ------------------------- */

        .card-actions {

            display: flex;

            align-items: center;

            gap: 10px;

            margin-top: 22px;

        }


        .action {

            padding: 10px 15px;

            border:
                    1px solid #303030;

            background: transparent;

            color: #aaa;

            text-decoration: none;

            font-size: 9px;

            letter-spacing: 0.12em;

            text-transform: uppercase;

            cursor: pointer;

            transition:
                    background 0.2s ease,
                    color 0.2s ease,
                    border-color 0.2s ease;

        }


        .action:hover {

            background: #ffffff;

            border-color: #ffffff;

            color: #000000;

        }


        .action.delete:hover {

            background: #ff6b45;

            border-color: #ff6b45;

            color: #000000;

        }


        /* -------------------------
           EMPTY STATE
        ------------------------- */

        .empty {

            max-width: 1100px;

            margin: auto;

            padding: 100px 30px;

            border:
                    1px solid #252525;

            text-align: center;

        }


        .empty-mark {

            font-family:
                    Georgia,
                    serif;

            font-size: 60px;

            color: #333;

            margin-bottom: 20px;

        }


        .empty h2 {

            font-family:
                    Georgia,
                    serif;

            font-size: 32px;

            font-weight: 400;

            margin-bottom: 15px;

        }


        .empty p {

            color: #666;

            font-size: 13px;

            margin-bottom: 30px;

        }


        /* -------------------------
           RESPONSIVE
        ------------------------- */

        @media (max-width: 800px) {

            body {

                padding: 0 5vw 60px;

            }


            .capsules {

                grid-template-columns: 1fr;

            }


            .page-header {

                margin-top: 60px;

            }


            .capsule-card {

                min-height: 300px;

            }

        }


        @media (max-width: 500px) {

            nav {

                height: 75px;

            }


            .nav-user {

                display: none;

            }


            h1 {

                font-size: 55px;

            }


            .capsule-card {

                padding: 24px;

            }


            .capsule-title {

                font-size: 27px;

            }


            .card-actions {

                flex-wrap: wrap;

            }

        }


        /* =========================
           DELETE MODAL
        ========================= */

        .modal-overlay {

            position: fixed;

            inset: 0;

            z-index: 1000;

            display: flex;

            align-items: center;

            justify-content: center;

            padding: 25px;

            background: rgba(0, 0, 0, 0.78);

            backdrop-filter: blur(8px);

            opacity: 0;

            visibility: hidden;

            transition:
                    opacity 0.25s ease,
                    visibility 0.25s ease;
        }


        .modal-overlay.active {

            opacity: 1;

            visibility: visible;
        }


        .delete-modal {

            width: 100%;

            max-width: 430px;

            padding: 38px;

            background: #101010;

            border:
                    1px solid #2d2d2d;

            transform: translateY(15px) scale(0.98);

            transition:
                    transform 0.25s ease;
        }


        .modal-overlay.active .delete-modal {

            transform: translateY(0) scale(1);
        }


        .modal-icon {

            width: 42px;

            height: 42px;

            display: flex;

            align-items: center;

            justify-content: center;

            border:
                    1px solid #333;

            border-radius: 50%;

            color: #ff6b45;

            font-family: Georgia, serif;

            font-size: 25px;

            margin-bottom: 25px;
        }


        .modal-label {

            color: #555;

            font-family: monospace;

            font-size: 8px;

            letter-spacing: 0.2em;

            text-transform: uppercase;

            margin-bottom: 12px;
        }


        .delete-modal h2 {

            font-family:
                    Georgia,
                    "Times New Roman",
                    serif;

            font-size: 32px;

            font-weight: 400;

            letter-spacing: -0.04em;

            margin-bottom: 15px;
        }


        .delete-modal p {

            color: #666;

            font-size: 12px;

            line-height: 1.7;

            margin-bottom: 30px;
        }


        .modal-actions {

            display: flex;

            align-items: center;

            justify-content: flex-end;

            gap: 10px;
        }


        .modal-cancel,
        .modal-delete {

            padding: 12px 18px;

            font-size: 9px;

            font-weight: 700;

            letter-spacing: 0.12em;

            text-transform: uppercase;

            cursor: pointer;

        }


        .modal-cancel {

            border:
                    1px solid #303030;

            background: transparent;

            color: #888;
        }


        .modal-cancel:hover {

            color: #fff;

            border-color: #555;
        }


        .modal-delete {

            border:
                    1px solid #ff6b45;

            background: #ff6b45;

            color: #080808;
        }


        .modal-delete:hover {

            background: #ff815f;

            border-color: #ff815f;
        }

        .modal-icon {
            width: 42px;
            height: 42px;

            display: flex;
            align-items: center;
            justify-content: center;

            border: 1px solid #333;
            border-radius: 50%;

            background: transparent;
            color: #ff6b45;

            font-family: Georgia, serif;
            font-size: 25px;

            cursor: pointer;

            margin-bottom: 25px;

            transition:
                background 0.2s ease,
                color 0.2s ease,
                border-color 0.2s ease;
        }

        .modal-icon:hover {
            background: #ff6b45;
            border-color: #ff6b45;
            color: #080808;
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
                href="logout"
                class="logout">
            Logout
        </a>

    </div>

</nav>


<!-- =========================
     HEADER
========================= -->

<header class="page-header">

    <div class="eyebrow">
        Your private archive
    </div>


    <h1>
        My <em>Capsules.</em>
    </h1>


    <p class="subtitle">

        Messages you've written for another
        version of yourself. Some are waiting.
        Some have already arrived.

    </p>


    <div class="header-actions">

        <a
                href="create-capsule.html"
                class="create-button">
            + Create Capsule
        </a>

    </div>

</header>


<!-- =========================
     CAPSULES
========================= -->

<% if (capsules == null || capsules.isEmpty()) { %>


    <section class="empty">

        <div class="empty-mark">
            ∅
        </div>


        <h2>
            Nothing sealed yet.
        </h2>


        <p>
            Write something today that
            your future self can discover.
        </p>


        <a
                href="create-capsule.html"
                class="create-button">
            Create Your First Capsule
        </a>

    </section>


<% } else { %>


    <section class="capsules">


        <% for (TimeCapsule capsule : capsules) { %>


            <article class="capsule-card">


                <!-- CARD TOP -->

                <div class="card-top">

                    <div class="status
                        <%= capsule.getStatus().equals("PENDING")
                                ? "pending"
                                : "delivered" %>">

                        <span class="status-dot"></span>

                        <%= capsule.getStatus() %>

                    </div>


                    <span class="card-number">

                        #<%= capsule.getId() %>

                    </span>

                </div>


                <!-- CONTENT -->

                <h2 class="capsule-title">

                    <%= capsule.getTitle() %>

                </h2>


                <p class="capsule-message">

                    <%= capsule.getMessage() %>

                </p>


                <!-- DATE -->

                <div class="card-date">

                    <div class="date-label">

                        <%= capsule.getStatus().equals("DELIVERED")
                                ? "Delivered"
                                : "Opens On" %>

                    </div>


                    <div class="date-value">

                        <%= capsule.getDeliveryDate() %>

                    </div>

                </div>


                <!-- ACTIONS -->

                <div class="card-actions">


                    <a
                            href="view-capsule?id=<%= capsule.getId() %>"
                            class="action">
                        View
                    </a>


                    <% if (capsule.getStatus()
                            .equals("PENDING")) { %>


                        <a
                                href="edit-capsule?id=<%= capsule.getId() %>"
                                class="action">
                            Edit
                        </a>


                       <button
                               type="button"
                               class="action delete"
                               onclick="openDeleteModal(<%= capsule.getId() %>)">
                           Delete
                       </button>


                    <% } %>


                </div>

            </article>


        <% } %>


    </section>


<% } %>


<!-- =========================
     DELETE MODAL
========================= -->

<div
        class="modal-overlay"
        id="deleteModal">

    <div class="delete-modal">

       <button
               type="button"
               class="modal-icon"
               onclick="closeDeleteModal()"
               aria-label="Close">
           ×
       </button>

        <div class="modal-label">
            Permanent Action
        </div>

        <h2>
            Delete this capsule?
        </h2>

        <p>
            This capsule will be permanently removed
            from your archive. This action cannot be undone.
        </p>

        <div class="modal-actions">

            <button
                    type="button"
                    class="modal-cancel"
                    onclick="closeDeleteModal()">
                Cancel
            </button>

            <form
                    id="deleteForm"
                    action="delete-capsule"
                    method="post">

                <input
                        type="hidden"
                        id="deleteCapsuleId"
                        name="id"
                >

                <button
                        type="submit"
                        class="modal-delete">
                    Delete Capsule
                </button>

            </form>

        </div>

    </div>

</div>

<script>

    const deleteModal =
        document.getElementById("deleteModal");

    const deleteCapsuleId =
        document.getElementById("deleteCapsuleId");


    function openDeleteModal(capsuleId) {

        deleteCapsuleId.value =
            capsuleId;

        deleteModal.classList.add(
            "active"
        );

    }


    function closeDeleteModal() {

        deleteModal.classList.remove(
            "active"
        );

    }


    /*
     * Close when clicking outside
     */

    deleteModal.addEventListener(
        "click",
        function (event) {

            if (event.target === deleteModal) {

                closeDeleteModal();

            }

        }
    );


    /*
     * Close with Escape key
     */

    document.addEventListener(
        "keydown",
        function (event) {

            if (event.key === "Escape") {

                closeDeleteModal();

            }

        }
    );

</script>


</body>

</html>