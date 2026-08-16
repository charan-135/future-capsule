package com.futurecapsule.listener;

import com.futurecapsule.scheduler.CapsuleDeliveryScheduler;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SchedulerListener
        implements ServletContextListener {

    private CapsuleDeliveryScheduler scheduler;

    @Override
    public void contextInitialized(
            ServletContextEvent event) {

        System.out.println(
                "================================"
        );

        System.out.println(
                "Future Capsule application started."
        );

        System.out.println(
                "Starting Capsule Delivery Scheduler..."
        );

        System.out.println(
                "================================"
        );

        scheduler =
                new CapsuleDeliveryScheduler();

        scheduler.start();
    }

    @Override
    public void contextDestroyed(
            ServletContextEvent event) {

        System.out.println(
                "================================"
        );

        System.out.println(
                "Future Capsule application stopping."
        );

        System.out.println(
                "Stopping Capsule Delivery Scheduler..."
        );

        System.out.println(
                "================================"
        );

        if (scheduler != null) {

            scheduler.stop();
        }
    }
}