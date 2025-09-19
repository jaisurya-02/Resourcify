package com.resourcify.model;

public class Booking {
    private int bookingId;
    private int userId;
    private int resourceId;
    private String date;
    private String timeSlot;
    private String status;
    
    // Additional fields for display purposes
    private String resourceName;
    private String resourceType;
    private String userName;
    
    public Booking(int bookingId, int userId, int resourceId, String date, String timeSlot, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.resourceId = resourceId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }
    
    // Getters
    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public int getResourceId() { return resourceId; }
    public String getDate() { return date; }
    public String getTimeSlot() { return timeSlot; }
    public String getStatus() { return status; }
    public String getResourceName() { return resourceName; }
    public String getResourceType() { return resourceType; }
    public String getUserName() { return userName; }
    
    // Setters
    public void setDate(String date) { this.date = date; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setStatus(String status) { this.status = status; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public boolean requestBooking() {
        // This method would typically interact with the database
        // For now, we'll just update the status
        this.status = "pending";
        return true;
    }
    
    public boolean cancelBooking() {
        // This method would typically remove the booking from database
        // For now, we'll just update the status
        this.status = "cancelled";
        return true;
    }
    
    public boolean isApproved() {
        return "approved".equalsIgnoreCase(this.status);
    }
    
    public boolean isPending() {
        return "pending".equalsIgnoreCase(this.status);
    }
    
    public boolean isRejected() {
        return "rejected".equalsIgnoreCase(this.status);
    }
    
    @Override
    public String toString() {
        return "Booking{" +
               "bookingId=" + bookingId +
               ", userId=" + userId +
               ", resourceId=" + resourceId +
               ", date='" + date + '\'' +
               ", timeSlot='" + timeSlot + '\'' +
               ", status='" + status + '\'' +
               '}';
    }
    
    public String getDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking ID: ").append(bookingId).append("\n");
        sb.append("Resource: ").append(resourceName != null ? resourceName : "Unknown").append("\n");
        sb.append("Type: ").append(resourceType != null ? resourceType : "Unknown").append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Time Slot: ").append(timeSlot).append("\n");
        sb.append("Status: ").append(status.toUpperCase()).append("\n");
        if (userName != null) {
            sb.append("Booked by: ").append(userName).append("\n");
        }
        return sb.toString();
    }
    
    public String getShortDisplayString() {
        String statusIcon = getStatusIcon();
        return String.format("%s ID:%d | %s | %s | %s | %s", 
                           statusIcon, bookingId, 
                           resourceName != null ? resourceName : "Resource#" + resourceId,
                           date, timeSlot, status.toUpperCase());
    }
    
    private String getStatusIcon() {
        switch (status.toLowerCase()) {
            case "approved": return "✓";
            case "rejected": return "✗";
            case "pending": return "⏳";
            default: return "●";
        }
    }
}