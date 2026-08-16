package com.futurecapsule.scheduler;

import com.futurecapsule.dao.TimeCapsuleDAO;
import com.futurecapsule.model.TimeCapsule;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeliveryScheduler {

    private final TimeCapsuleDAO capsuleDAO;

    private final ScheduledExecutorService scheduler;

    public DeliveryScheduler() {

        capsuleDAO =
                new TimeCapsuleDAO();

        scheduler =
                Executors.newScheduledThreadPool(1);
    }


    public void start() {

        System.out.println(
                "Delivery Scheduler started."
        );

        scheduler.scheduleAtFixedRate(
                this::processDueCapsules,
                0,
                1,
                TimeUnit.MINUTES
        );
    }


    private void processDueCapsules() {

        System.out.println(
                "\nChecking for due capsules..."
        );

        List<TimeCapsule> capsules =
                capsuleDAO.findDueCapsules();

        if (capsules.isEmpty()) {

            System.out.println(
                    "No capsules are due."
            );

            return;
        }


        for (TimeCapsule capsule : capsules) {

            boolean delivered =
                    capsuleDAO.markAsDelivered(
                            capsule.getId()
                    );

            if (delivered) {

                System.out.println(
                        "Capsule delivered: ID "
                                + capsule.getId()
                );

            } else {

                System.out.println(
                        "Failed to deliver capsule: ID "
                                + capsule.getId()
                );
            }
        }
    }


    public void stop() {

        scheduler.shutdown();

        System.out.println(
                "Delivery Scheduler stopped."
        );
    }
}