package com.resourcify.model;

import com.resourcify.database.DatabaseManager;
import java.util.List;

public class Student extends User {
    
    public Student(int userId, String name, String email, String password) {
        super(userId, name, email, password);
    }
    
    @Override
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
    
    @Override
    public void logout() {
        System.out.println("Student " + name + " logged out successfully.");
    }
    
    @Override
    public String getRole() {
        return "student";
    }
    
    public boolean bookResource(int resourceId, String date, String timeSlot) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean success = dbManager.createBooking(this.userId, resourceId, date, timeSlot);
        
        if (success) {
            System.out.println("Booking request submitted successfully!");
            System.out.println("Your booking is pending approval.");
        } else {
            System.out.println("Failed to create booking. Resource might be already booked for this time slot.");
        }
        
        return success;
    }
    
    public List<Booking> viewBookings() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        return dbManager.getUserBookings(this.userId);
    }
    
    public boolean cancelBooking(int bookingId) {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        return dbManager.cancelBooking(bookingId, this.userId);
    }
}