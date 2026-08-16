package com.futurecapsule.model;

import java.time.LocalDateTime;

public class TimeCapsule {

    private int id;

    private int userId;

    private String title;

    private String message;

    private LocalDateTime deliveryDate;

    private String status;

    private LocalDateTime createdAt;


    // Constructor for creating a new capsule
    public TimeCapsule(
            int userId,
            String title,
            String message,
            LocalDateTime deliveryDate) {

        this.userId = userId;
        this.title = title;
        this.message = message;
        this.deliveryDate = deliveryDate;
        this.status = "PENDING";
    }


    // Constructor for retrieving a capsule from database
    public TimeCapsule(
            int id,
            int userId,
            String title,
            String message,
            LocalDateTime deliveryDate,
            String status,
            LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.createdAt = createdAt;
    }


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}