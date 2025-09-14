package com.resourcify.model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int userId;
    private int resourceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public Booking(int id, int userId, int resourceId, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.userId = userId;
        this.resourceId = resourceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getResourceId() { return resourceId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
