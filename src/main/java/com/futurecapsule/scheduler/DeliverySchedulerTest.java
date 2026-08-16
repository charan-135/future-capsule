package com.futurecapsule.scheduler;

import com.futurecapsule.dao.TimeCapsuleDAO;
import com.futurecapsule.model.TimeCapsule;

import java.util.List;

public class DeliverySchedulerTest {

    public static void main(String[] args) {

        System.out.println(
                "Delivery Scheduler started."
        );

        TimeCapsuleDAO capsuleDAO =
                new TimeCapsuleDAO();

        System.out.println(
                "Scheduler test is running..."
        );

        try {

            System.out.println();
            System.out.println(
                    "Checking for due capsules..."
            );

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

                System.out.println();
                System.out.println(
                        "--------------------------------"
                );

                System.out.println(
                        "Capsule ID: "
                                + capsule.getId()
                );

                System.out.println(
                        "User ID: "
                                + capsule.getUserId()
                );

                System.out.println(
                        "Title: "
                                + capsule.getTitle()
                );

                System.out.println(
                        "Delivery Date: "
                                + capsule.getDeliveryDate()
                );

                System.out.println(
                        "Status: "
                                + capsule.getStatus()
                );

                System.out.println(
                        "--------------------------------"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Scheduler test failed."
            );

            e.printStackTrace();
        }
    }
}