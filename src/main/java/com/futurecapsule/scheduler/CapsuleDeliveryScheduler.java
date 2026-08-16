package com.futurecapsule.scheduler;

import com.futurecapsule.dao.TimeCapsuleDAO;
import com.futurecapsule.dao.UserDAO;
import com.futurecapsule.model.TimeCapsule;
import com.futurecapsule.model.User;
import com.futurecapsule.service.EmailService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CapsuleDeliveryScheduler {

    private final TimeCapsuleDAO capsuleDAO;
    private final UserDAO userDAO;
    private final EmailService emailService;

    private final ScheduledExecutorService scheduler;

    public CapsuleDeliveryScheduler() {

        capsuleDAO =
                new TimeCapsuleDAO();

        userDAO =
                new UserDAO();

        emailService =
                new EmailService();

        scheduler =
                Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {

        System.out.println(
                "Capsule Delivery Scheduler started."
        );

        // Check immediately when the scheduler starts.
        deliverDueCapsules();

        // Then check every 1 minute.
        scheduler.scheduleAtFixedRate(
                this::deliverDueCapsules,
                1,
                1,
                TimeUnit.MINUTES
        );
    }

    private void deliverDueCapsules() {

        System.out.println();
        System.out.println(
                "Checking for due capsules..."
        );

        try {

            List<TimeCapsule> capsules =
                    capsuleDAO.findDueCapsules();

            if (capsules.isEmpty()) {

                System.out.println(
                        "No capsules are due."
                );

                return;
            }

            System.out.println(
                    "Found "
                            + capsules.size()
                            + " due capsule(s)."
            );

            for (TimeCapsule capsule : capsules) {

                deliverCapsule(capsule);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while checking capsules."
            );

            e.printStackTrace();
        }
    }

    private void deliverCapsule(
            TimeCapsule capsule) {

        System.out.println();
        System.out.println(
                "Processing capsule ID: "
                        + capsule.getId()
        );

        try {

            // Find the owner of the capsule.
            User user =
                    userDAO.findUserById(
                            capsule.getUserId()
                    );

            if (user == null) {

                System.out.println(
                        "User not found for capsule ID: "
                                + capsule.getId()
                );

                return;
            }

            String recipientEmail =
                    user.getEmail();

            if (recipientEmail == null ||
                    recipientEmail.isBlank()) {

                System.out.println(
                        "User has no email address."
                );

                return;
            }

            System.out.println(
                    "Sending capsule to: "
                            + recipientEmail
            );

            /*
             * Send the email first.
             *
             * If this throws an exception,
             * the capsule remains PENDING.
             */
            emailService.sendCapsuleEmail(
                    recipientEmail,
                    capsule,
                    user.getName()
            );

            /*
             * Only mark the capsule DELIVERED
             * after the email was successfully sent.
             */
            boolean markedDelivered =
                    capsuleDAO.markAsDelivered(
                            capsule.getId()
                    );

            if (markedDelivered) {

                System.out.println(
                        "Capsule ID "
                                + capsule.getId()
                                + " marked as DELIVERED."
                );

            } else {

                System.out.println(
                        "Email was sent, but capsule ID "
                                + capsule.getId()
                                + " could not be marked as DELIVERED."
                );
            }

        } catch (Exception e) {

            /*
             * IMPORTANT:
             *
             * We do NOT mark the capsule as DELIVERED
             * when email sending fails.
             *
             * It remains PENDING and will be retried
             * during the next scheduler check.
             */
            System.out.println(
                    "Failed to deliver capsule ID "
                            + capsule.getId()
            );

            e.printStackTrace();
        }
    }

    public void stop() {

        System.out.println(
                "Stopping Capsule Delivery Scheduler..."
        );

        scheduler.shutdown();

        try {

            if (!scheduler.awaitTermination(
                    5,
                    TimeUnit.SECONDS)) {

                scheduler.shutdownNow();
            }

        } catch (InterruptedException e) {

            scheduler.shutdownNow();

            Thread.currentThread().interrupt();
        }

        System.out.println(
                "Capsule Delivery Scheduler stopped."
        );
    }
}