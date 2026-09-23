package com.foodapp.model;

import java.sql.Timestamp;

public class Tracking {

    private int trackingId;
    private int orderId;
    private String currentStatus;
    private Timestamp updatedAt;

    public Tracking() {
    }

    public Tracking(int trackingId, int orderId, String currentStatus, Timestamp updatedAt) {
        this.trackingId = trackingId;
        this.orderId = orderId;
        this.currentStatus = currentStatus;
        this.updatedAt = updatedAt;
    }

    public int getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
