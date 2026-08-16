package com.futurecapsule.service;

import com.futurecapsule.model.TimeCapsule;

import java.time.LocalDateTime;
import java.util.List;

public class TimeCapsuleServiceTest {

    public static void main(String[] args) {

        TimeCapsuleService service =
                new TimeCapsuleService();


        // ----- Creating Capsule -----

        System.out.println("----- Creating Capsule -----");

        int capsuleId =
                service.createCapsule(
                        1,
                        "Service Layer Test",
                        "This capsule was created through the service.",
                        LocalDateTime.of(
                                2035,
                                8,
                                15,
                                10,
                                0
                        )
                );

        System.out.println(
                "Capsule created successfully!"
        );

        System.out.println(
                "Capsule ID: " + capsuleId
        );


        // ----- Finding Capsules -----

        System.out.println(
                "\n----- Finding User Capsules -----"
        );

        List<TimeCapsule> capsules =
                service.getCapsulesByUser(1);

        for (TimeCapsule capsule : capsules) {

            System.out.println(
                    "ID: " + capsule.getId()
            );

            System.out.println(
                    "Title: " + capsule.getTitle()
            );

            System.out.println(
                    "Delivery Date: "
                            + capsule.getDeliveryDate()
            );

            System.out.println(
                    "Status: " + capsule.getStatus()
            );

            System.out.println("--------------------");
        }


        // ----- Finding One Capsule -----

        System.out.println(
                "\n----- Finding Capsule By ID -----"
        );

        TimeCapsule foundCapsule =
                service.getCapsule(capsuleId);

        if (foundCapsule != null) {

            System.out.println(
                    "Capsule found!"
            );

            System.out.println(
                    "ID: " + foundCapsule.getId()
            );

            System.out.println(
                    "Title: " + foundCapsule.getTitle()
            );

            System.out.println(
                    "Message: " + foundCapsule.getMessage()
            );

            System.out.println(
                    "Delivery Date: "
                            + foundCapsule.getDeliveryDate()
            );

            System.out.println(
                    "Status: " + foundCapsule.getStatus()
            );

        } else {

            System.out.println(
                    "Capsule not found."
            );
        }


        // ----- Updating Capsule -----

        System.out.println(
                "\n----- Updating Capsule -----"
        );

        boolean updated =
                service.updateCapsule(
                        capsuleId,
                        1,
                        "Updated Service Message",
                        "This capsule was updated through the service.",
                        LocalDateTime.of(
                                2036,
                                8,
                                15,
                                10,
                                0
                        )
                );

        if (updated) {

            System.out.println(
                    "Capsule updated successfully!"
            );

        } else {

            System.out.println(
                    "Capsule update failed."
            );
        }


        // ----- Deleting Capsule -----

        System.out.println(
                "\n----- Deleting Capsule -----"
        );

        boolean deleted =
                service.deleteCapsule(
                        capsuleId,
                        1
                );

        if (deleted) {

            System.out.println(
                    "Capsule deleted successfully!"
            );

        } else {

            System.out.println(
                    "Capsule deletion failed."
            );
        }
    }
}