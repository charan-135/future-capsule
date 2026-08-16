package com.futurecapsule.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import com.futurecapsule.model.TimeCapsule;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EmailService {

    private final String username;
    private final String password;

    public EmailService() {

        username = System.getenv("FUTURE_CAPSULE_EMAIL");
        password = System.getenv("FUTURE_CAPSULE_EMAIL_PASSWORD");

        if (username == null || username.isBlank()) {
            throw new IllegalStateException(
                    "FUTURE_CAPSULE_EMAIL environment variable is not set."
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "FUTURE_CAPSULE_EMAIL_PASSWORD environment variable is not set."
            );
        }
    }

    public void sendCapsuleEmail(
            String recipientEmail,
            TimeCapsule capsule,
            String recipientName)
            throws MessagingException {

        Properties properties = new Properties();

        properties.put(
                "mail.smtp.host",
                "smtp.gmail.com"
        );

        properties.put(
                "mail.smtp.port",
                "587"
        );

        properties.put(
                "mail.smtp.auth",
                "true"
        );

        properties.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {

                            @Override
                            protected PasswordAuthentication
                            getPasswordAuthentication() {

                                return new PasswordAuthentication(
                                        username,
                                        password
                                );
                            }
                        }
                );

        Message email =
                new MimeMessage(session);

        email.setFrom(
                new InternetAddress(username)
        );

        email.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail)
        );

        email.setSubject(
                "Your Future Capsule Has Arrived"
        );

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMMM yyyy, hh:mm a"
                );

        String createdDate =
                capsule.getCreatedAt() != null
                        ? capsule.getCreatedAt().format(formatter)
                        : "Unknown";

        String deliveryDate =
                capsule.getDeliveryDate() != null
                        ? capsule.getDeliveryDate().format(formatter)
                        : "Unknown";

        String emailBody = """
                Hello %s,

                Your Future Capsule has arrived.

                ----------------------------------------

                TITLE
                %s

                MESSAGE
                %s

                ----------------------------------------

                Written on:
                %s

                Delivered on:
                %s

                Your future self was waiting for this message.

                — Future Capsule
                """.formatted(
                recipientName,
                capsule.getTitle(),
                capsule.getMessage(),
                createdDate,
                deliveryDate
        );

        email.setText(emailBody);

        Transport.send(email);

        System.out.println(
                "Capsule email sent successfully to: "
                        + recipientEmail
        );
    }
}