package com.futurecapsule.dao;

import com.futurecapsule.model.TimeCapsule;

import java.time.LocalDateTime;
import java.util.List;

public class TimeCapsuleDAOTest {

    public static void main(String[] args) {

        TimeCapsuleDAO capsuleDAO =
                new TimeCapsuleDAO();

        // ----- Creating Capsule -----

        TimeCapsule newCapsule =
                new TimeCapsule(
                        1,
                        "Message to Future Me",
                        "I hope you achieved your dreams!",
                        LocalDateTime.of(
                                2030,
                                8,
                                15,
                                9,
                                0
                        )
                );

        int capsuleId =
                capsuleDAO.createCapsule(newCapsule);

        if (capsuleId != -1) {

            System.out.println(
                    "Capsule created successfully!"
            );

            System.out.println(
                    "Capsule ID: " + capsuleId
            );

        } else {

            System.out.println(
                    "Failed to create capsule."
            );
        }


        // ----- Finding Capsules -----

        System.out.println("\n----- Finding Capsules -----");

        int userId = 1;

        List<TimeCapsule> capsules =
                capsuleDAO.findCapsulesByUser(userId);

        for (TimeCapsule capsule : capsules) {

            System.out.println(
                    "ID: " + capsule.getId()
            );

            System.out.println(
                    "Title: " + capsule.getTitle()
            );

            System.out.println(
                    "Message: " + capsule.getMessage()
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

        System.out.println("\n----- Finding Capsule By ID -----");

        int searchId = 3;

        TimeCapsule foundCapsule =
                capsuleDAO.findCapsuleById(searchId);

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

        System.out.println("\n----- Updating Capsule -----");

        int updateCapsuleId = 3;

        boolean updated =
                capsuleDAO.updateCapsule(
                        updateCapsuleId,
                        1,
                        "Updated Future Message",
                        "This message was updated successfully!",
                        LocalDateTime.of(
                                2031,
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

        System.out.println("\n----- Deleting Capsule -----");

        int deleteCapsuleId = 7;

        boolean deleted =
                capsuleDAO.deleteCapsule(
                        deleteCapsuleId,
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