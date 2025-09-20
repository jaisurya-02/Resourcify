package com.resourcify.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.resourcify.model.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/resourcify_db";
    private static final String USERNAME = "root"; // Change as per your MySQL setup
    private static final String PASSWORD = "password"; // Change as per your MySQL setup
    
    private static DatabaseManager instance;
    private Connection connection;
    
    private DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    // User-related operations
    public User authenticateUser(String email, String password) {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return null;
    }
    
    public boolean createUser(String name, String email, String password, String role) {
        String query = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return false;
    }
    
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String role = rs.getString("role");
        
        switch (role.toLowerCase()) {
            case "student":
                return new Student(userId, name, email, password);
            case "faculty":
                return new Faculty(userId, name, email, password);
            case "admin":
                return new Admin(userId, name, email, password);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
    
    // Resource-related operations
    public List<Resource> getAllResources() {
        List<Resource> resources = new ArrayList<>();
        String query = "SELECT * FROM Resources";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Resource resource = new Resource(
                    rs.getInt("resource_id"),
                    rs.getString("resource_name"),
                    rs.getString("type"),
                    rs.getBoolean("availability")
                );
                resources.add(resource);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching resources: " + e.getMessage());
        }
        return resources;
    }
    
    public boolean addResource(String name, String type, boolean availability) {
        String query = "INSERT INTO Resources (resource_name, type, availability) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setBoolean(3, availability);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding resource: " + e.getMessage());
        }
        return false;
    }
    
    public boolean removeResource(int resourceId) {
        String query = "DELETE FROM Resources WHERE resource_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, resourceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing resource: " + e.getMessage());
        }
        return false;
    }
    
    public boolean updateResourceAvailability(int resourceId, boolean availability) {
        String query = "UPDATE Resources SET availability = ? WHERE resource_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, availability);
            stmt.setInt(2, resourceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating resource availability: " + e.getMessage());
        }
        return false;
    }
    
    // Booking-related operations
    public boolean createBooking(int userId, int resourceId, String date, String timeSlot) {
        String query = "INSERT INTO Bookings (user_id, resource_id, date, time_slot, status) VALUES (?, ?, ?, ?, 'pending')";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, resourceId);
            stmt.setString(3, date);
            stmt.setString(4, timeSlot);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error
                System.err.println("Booking conflict: Resource is already booked for this time slot.");
            } else {
                System.err.println("Error creating booking: " + e.getMessage());
            }
        }
        return false;
    }
    
    public List<Booking> getUserBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = """
            SELECT b.booking_id, b.user_id, b.resource_id, b.date, b.time_slot, b.status,
                   r.resource_name, r.type, u.name as user_name
            FROM Bookings b
            JOIN Resources r ON b.resource_id = r.resource_id
            JOIN Users u ON b.user_id = u.user_id
            WHERE b.user_id = ?
            ORDER BY b.date, b.time_slot
        """;
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("resource_id"),
                    rs.getString("date"),
                    rs.getString("time_slot"),
                    rs.getString("status")
                );
                booking.setResourceName(rs.getString("resource_name"));
                booking.setResourceType(rs.getString("type"));
                booking.setUserName(rs.getString("user_name"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = """
            SELECT b.booking_id, b.user_id, b.resource_id, b.date, b.time_slot, b.status,
                   r.resource_name, r.type, u.name as user_name
            FROM Bookings b
            JOIN Resources r ON b.resource_id = r.resource_id
            JOIN Users u ON b.user_id = u.user_id
            ORDER BY b.date, b.time_slot
        """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("resource_id"),
                    rs.getString("date"),
                    rs.getString("time_slot"),
                    rs.getString("status")
                );
                booking.setResourceName(rs.getString("resource_name"));
                booking.setResourceType(rs.getString("type"));
                booking.setUserName(rs.getString("user_name"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    public boolean updateBookingStatus(int bookingId, String status) {
        String query = "UPDATE Bookings SET status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
        }
        return false;
    }
    
    public boolean cancelBooking(int bookingId, int userId) {
        String query = "DELETE FROM Bookings WHERE booking_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error canceling booking: " + e.getMessage());
        }
        return false;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}