package com.resourcify.model;

import com.resourcify.database.DatabaseManager;
import java.util.List;

public class Admin extends User {
    
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password);
    }
    
    @Override
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
    
    @Override
    public void logout() {
        System.out.println("Admin " + name + " logged out successfully.");
    }
    
    @Override
    public String getRole() {
        return "admin";
    }
    
    public boolean addResource(String name, String type, boolean availability) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean success = dbManager.addResource(name, type, availability);
        
        if (success) {
            System.out.println("Resource added successfully: " + name);
        } else {
            System.out.println("Failed to add resource: " + name);
        }
        
        return success;
    }
    
    public boolean removeResource(int resourceId) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean success = dbManager.removeResource(resourceId);
        
        if (success) {
            System.out.println("Resource removed successfully!");
        } else {
            System.out.println("Failed to remove resource.");
        }
        
        return success;
    }
    
    public List<Resource> viewAllResources() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        return dbManager.getAllResources();
    }
    
    public boolean updateResourceAvailability(int resourceId, boolean availability) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        return dbManager.updateResourceAvailability(resourceId, availability);
    }
    
    public List<Booking> manageBookings() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        return dbManager.getAllBookings();
    }
    
    public boolean approveBooking(int bookingId) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean success = dbManager.updateBookingStatus(bookingId, "approved");
        
        if (success) {
            System.out.println("Booking approved successfully!");
        } else {
            System.out.println("Failed to approve booking.");
        }
        
        return success;
    }
    
    public boolean rejectBooking(int bookingId) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean success = dbManager.updateBookingStatus(bookingId, "rejected");
        
        if (success) {
            System.out.println("Booking rejected successfully!");
        } else {
            System.out.println("Failed to reject booking.");
        }
        
        return success;
    }
    
    public boolean createUser(String name, String email, String password, String role) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        return dbManager.createUser(name, email, password, role);
    }
}