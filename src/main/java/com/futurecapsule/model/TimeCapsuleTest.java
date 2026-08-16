package com.futurecapsule.model;

import java.time.LocalDateTime;

public class TimeCapsuleTest {

    public static void main(String[] args) {

        TimeCapsule capsule =
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

        System.out.println("User ID: "
                + capsule.getUserId());

        System.out.println("Title: "
                + capsule.getTitle());

        System.out.println("Message: "
                + capsule.getMessage());

        System.out.println("Delivery Date: "
                + capsule.getDeliveryDate());

        System.out.println("Status: "
                + capsule.getStatus());
    }
}