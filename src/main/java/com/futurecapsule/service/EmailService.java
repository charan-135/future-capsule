package com.futurecapsule.service;

import com.futurecapsule.model.TimeCapsule;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import java.time.format.DateTimeFormatter;

public class EmailService {

    private final Resend resend;
    private final String senderEmail;

    public EmailService() {

        String apiKey =
                System.getenv("RESEND_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {

            throw new IllegalStateException(
                    "RESEND_API_KEY environment variable is not set."
            );
        }

        /*
         * Resend's onboarding sender can be used
         * for initial testing.
         *
         * Later, after verifying your own domain,
         * change this to your verified sender address.
         */
        senderEmail =
                "onboarding@resend.dev";

        resend =
                new Resend(apiKey);
    }

    public void sendCapsuleEmail(
            String recipientEmail,
            TimeCapsule capsule,
            String recipientName)
            throws Exception {

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

        CreateEmailOptions params =
                CreateEmailOptions.builder()
                        .from(
                                "Future Capsule <"
                                        + senderEmail
                                        + ">"
                        )
                        .to(recipientEmail)
                        .subject(
                                "Your Future Capsule Has Arrived"
                        )
                        .text(emailBody)
                        .build();

        try {

            CreateEmailResponse response =
                    resend.emails().send(params);

            System.out.println(
                    "Capsule email sent successfully."
            );

            System.out.println(
                    "Resend Email ID: "
                            + response.getId()
            );

        } catch (ResendException e) {

            System.out.println(
                    "Resend failed to send capsule email."
            );

            e.printStackTrace();

            throw e;
        }
    }
}