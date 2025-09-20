package com.resourcify.model;

public class Resource {
    private int resourceId;
    private String resourceName;
    private String type;
    private boolean availability;
    
    public Resource(int resourceId, String resourceName, String type, boolean availability) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.type = type;
        this.availability = availability;
    }
    
    public int getResourceId() { return resourceId; }
    public String getResourceName() { return resourceName; }
    public String getType() { return type; }
    public boolean isAvailable() { return availability; }
    
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
    public void setType(String type) { this.type = type; }
    public void setAvailability(boolean availability) { this.availability = availability; }
    
    public boolean checkAvailability() {
        return this.availability;
    }
    
    public void updateAvailability(boolean availability) {
        this.availability = availability;
    }
    
    @Override
    public String toString() {
        return "Resource{" +
               "resourceId=" + resourceId +
               ", resourceName='" + resourceName + '\'' +
               ", type='" + type + '\'' +
               ", availability=" + availability +
               '}';
    }
    
    public String getDisplayString() {
        String status = availability ? "Available" : "Unavailable";
        return String.format("ID: %d | Name: %s | Type: %s | Status: %s", 
                           resourceId, resourceName, type, status);
    }
}