package com.futurecapsule.service;

import com.futurecapsule.dao.TimeCapsuleDAO;
import com.futurecapsule.model.TimeCapsule;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class TimeCapsuleService {

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");

    private final TimeCapsuleDAO capsuleDAO;

    public TimeCapsuleService() {
        this.capsuleDAO = new TimeCapsuleDAO();
    }

    public int createCapsule(
            int userId,
            String title,
            String message,
            LocalDateTime deliveryDate) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Title cannot be empty."
            );
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException(
                    "Message cannot be empty."
            );
        }

        if (deliveryDate == null) {
            throw new IllegalArgumentException(
                    "Delivery date is required."
            );
        }

        /*
         * deliveryDate comes from the browser as IST.
         *
         * Therefore compare it with the current IST time.
         */
        LocalDateTime nowIST =
                LocalDateTime.now(IST);

        if (!deliveryDate.isAfter(nowIST)) {
            throw new IllegalArgumentException(
                    "Delivery date must be in the future."
            );
        }

        TimeCapsule capsule =
                new TimeCapsule(
                        userId,
                        title,
                        message,
                        deliveryDate
                );

        return capsuleDAO.createCapsule(capsule);
    }


    public List<TimeCapsule> getCapsulesByUser(
            int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        return capsuleDAO.findCapsulesByUser(userId);
    }


    public TimeCapsule getCapsule(
            int capsuleId) {

        if (capsuleId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid capsule ID."
            );
        }

        return capsuleDAO.findCapsuleById(
                capsuleId
        );
    }


    public boolean updateCapsule(
            int capsuleId,
            int userId,
            String title,
            String message,
            LocalDateTime deliveryDate) {

        if (capsuleId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid capsule ID."
            );
        }

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Title cannot be empty."
            );
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException(
                    "Message cannot be empty."
            );
        }

        if (deliveryDate == null) {
            throw new IllegalArgumentException(
                    "Delivery date is required."
            );
        }

        /*
         * Incoming deliveryDate is IST.
         */
        LocalDateTime nowIST =
                LocalDateTime.now(IST);

        if (!deliveryDate.isAfter(nowIST)) {
            throw new IllegalArgumentException(
                    "Delivery date must be in the future."
            );
        }

        return capsuleDAO.updateCapsule(
                capsuleId,
                userId,
                title,
                message,
                deliveryDate
        );
    }


    public boolean deleteCapsule(
            int capsuleId,
            int userId) {

        if (capsuleId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid capsule ID."
            );
        }

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        return capsuleDAO.deleteCapsule(
                capsuleId,
                userId
        );
    }
}