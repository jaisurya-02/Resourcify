package com.resourcify;

import com.resourcify.database.DatabaseManager;
import com.resourcify.model.*;
import com.resourcify.ui.ConsoleUI;

import java.util.Scanner;

public class ResourcifyApp {
    private static User currentUser;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        System.out.println("     WELCOME TO RESOURCIFY");
        System.out.println("   Campus Resource Booking System");
            
        scanner = new Scanner(System.in);
    
        }
        
        // Main application loop
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                // Show login menu
                showWelcomeMenu();
                int choice = getIntInput();
                
                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        showSampleCredentials();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // Show main menu based on user role
                showMainMenu();
                int choice = getIntInput();
                
                if (choice == 0) {
                    logout();
                } else {
                    handleMenuChoice(choice);
                }
            }
        }
                
        scanner.close();
        System.out.println("\nThank you for using Resourcify! Goodbye!");
    }
    
    private static void showWelcomeMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("         LOGIN MENU");
        System.out.println("=".repeat(30));
        System.out.println("1. Login");
        System.out.println("2. View Sample Credentials");
        System.out.println("3. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    
    private static void login() {
        System.out.println("\n" + "-".repeat(25));
        System.out.println("         LOGIN");
        System.out.println("-".repeat(25));
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (user != null) {
            currentUser = user;
            System.out.println("\n Login successful!");
            System.out.println("Welcome, " + user.getName() + " (" + user.getRole().toUpperCase() + ")");
        } else {
            System.out.println("\n Invalid credentials. Please try again.");
        }
    }
    
    private static void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("    MAIN MENU - " + currentUser.getName());
        System.out.println("    Role: " + currentUser.getRole().toUpperCase());
        System.out.println("=".repeat(40));
        
        switch (currentUser.getRole().toLowerCase()) {
            case "student":
                showStudentMenu();
                break;
            case "faculty":
                showFacultyMenu();
                break;
            case "admin":
                showAdminMenu();
                break;
        }
        
        System.out.println("0. Logout");
        System.out.print("\nEnter your choice: ");
    }
    
    private static void showStudentMenu() {
        System.out.println("1. View Available Resources");
        System.out.println("2. Book Resource");
        System.out.println("3. View My Bookings");
        System.out.println("4. Cancel Booking");
    }
    
    private static void showFacultyMenu() {
        System.out.println("1. View Available Resources");
        System.out.println("2. Book Resource");
        System.out.println("3. View My Bookings");
        System.out.println("4. Cancel Booking");
        System.out.println("5. View All Bookings");
        System.out.println("6. Approve/Reject Bookings");
    }
    
    private static void showAdminMenu() {
        System.out.println("1. View All Resources");
        System.out.println("2. Add Resource");
        System.out.println("3. Remove Resource");
        System.out.println("4. Update Resource Availability");
        System.out.println("5. View All Bookings");
        System.out.println("6. Approve/Reject Bookings");
        System.out.println("7. Create User Account");
    }
    
    private static void handleMenuChoice(int choice) {
        switch (currentUser.getRole().toLowerCase()) {
            case "student":
                handleStudentChoice(choice);
                break;
            case "faculty":
                handleFacultyChoice(choice);
                break;
            case "admin":
                handleAdminChoice(choice);
                break;
        }
    }
    
    private static void handleStudentChoice(int choice) {
        Student student = (Student) currentUser;
        
        switch (choice) {
            case 1:
                ui.displayResources(dbManager.getAllResources());
                break;
            case 2:
                bookResource(student);
                break;
            case 3:
                ui.displayBookings(student.viewBookings());
                break;
            case 4:
                cancelBooking(student);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void handleFacultyChoice(int choice) {
        Faculty faculty = (Faculty) currentUser;
        
        switch (choice) {
            case 1:
                ui.displayResources(dbManager.getAllResources());
                break;
            case 2:
                bookResource(faculty);
                break;
            case 3:
                ui.displayBookings(faculty.viewBookings());
                break;
            case 4:
                cancelBooking(faculty);
                break;
            case 5:
                ui.displayBookings(faculty.viewAllBookings());
                break;
            case 6:
                manageBookingApprovals(faculty);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void handleAdminChoice(int choice) {
        Admin admin = (Admin) currentUser;
        
        switch (choice) {
            case 1:
                ui.displayResources(admin.viewAllResources());
                break;
            case 2:
                addResource(admin);
                break;
            case 3:
                removeResource(admin);
                break;
            case 4:
                updateResourceAvailability(admin);
                break;
            case 5:
                ui.displayBookings(admin.manageBookings());
                break;
            case 6:
                manageBookingApprovals(admin);
                break;
            case 7:
                createUserAccount(admin);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void bookResource(Object user) {
        System.out.println("         BOOK RESOURCE");
        
        // Show available resources
        ui.displayResources(dbManager.getAllResources());
        
        System.out.print("\nEnter Resource ID: ");
        int resourceId = getIntInput();
        
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        
        System.out.print("Enter Time Slot (e.g., 09:00-11:00): ");
        String timeSlot = scanner.nextLine().trim();
        
        boolean success = false;
        if (user instanceof Student) {
            success = ((Student) user).bookResource(resourceId, date, timeSlot);
        } else if (user instanceof Faculty) {
            success = ((Faculty) user).bookResource(resourceId, date, timeSlot);
        }
        
        if (success) {
            System.out.println("\n Booking request submitted successfully!");
        }
    }
    
    private static void cancelBooking(Object user) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("        CANCEL BOOKING");
        System.out.println("-".repeat(30));
        
        // Show user's bookings
        if (user instanceof Student) {
            ui.displayBookings(((Student) user).viewBookings());
        } else if (user instanceof Faculty) {
            ui.displayBookings(((Faculty) user).viewBookings());
        }
        
        System.out.print("\nEnter Booking ID to cancel: ");
        int bookingId = getIntInput();
        
        boolean success = false;
        if (user instanceof Student) {
            success = ((Student) user).cancelBooking(bookingId);
        } else if (user instanceof Faculty) {
            success = ((Faculty) user).cancelBooking(bookingId);
        }
        
        if (success) {
            System.out.println("\n Booking cancelled successfully!");
        } else {
            System.out.println("\n Failed to cancel booking.");
        }
    }
    
    private static void addResource(Admin admin) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("         ADD RESOURCE");
        System.out.println("-".repeat(30));
        
        System.out.print("Resource Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.println("Resource Type:");
        System.out.println("1. classroom");
        System.out.println("2. lab");
        System.out.println("3. equipment");
        System.out.println("4. seminar_hall");
        System.out.print("Choose type (1-4): ");
        
        String[] types = {"classroom", "lab", "equipment", "seminar_hall"};
        int typeChoice = getIntInput();
        
        if (typeChoice < 1 || typeChoice > 4) {
            System.out.println("Invalid type selection.");
            return;
        }
        
        String type = types[typeChoice - 1];
        
        System.out.print("Available (true/false): ");
        boolean availability = Boolean.parseBoolean(scanner.nextLine().trim());
        
        admin.addResource(name, type, availability);
    }
    
    private static void removeResource(Admin admin) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("        REMOVE RESOURCE");
        System.out.println("-".repeat(30));
        
        // Show all resources
        ui.displayResources(admin.viewAllResources());
        
        System.out.print("\nEnter Resource ID to remove: ");
        int resourceId = getIntInput();
        
        admin.removeResource(resourceId);
    }
    
    private static void updateResourceAvailability(Admin admin) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("    UPDATE AVAILABILITY");
        System.out.println("-".repeat(30));
        
        // Show all resources
        ui.displayResources(admin.viewAllResources());
        
        System.out.print("\nEnter Resource ID: ");
        int resourceId = getIntInput();
        
        System.out.print("Set availability (true/false): ");
        boolean availability = Boolean.parseBoolean(scanner.nextLine().trim());
        
        if (admin.updateResourceAvailability(resourceId, availability)) {
            System.out.println("\n Resource availability updated successfully!");
        } else {
            System.out.println("\n Failed to update resource availability.");
        }
    }
    
    
    
    private static void createUserAccount(Admin admin) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("       CREATE USER");
        System.out.println("-".repeat(30));
        
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        System.out.println("Role:");
        System.out.println("1. student");
        System.out.println("2. faculty");
        System.out.println("3. admin");
        System.out.print("Choose role (1-3): ");
        
        String[] roles = {"student", "faculty", "admin"};
        int roleChoice = getIntInput();
        
        if (roleChoice < 1 || roleChoice > 3) {
            System.out.println("Invalid role selection.");
            return;
        }
        
        String role = roles[roleChoice - 1];
        
        if (admin.createUser(name, email, password, role)) {
            System.out.println("\n User account created successfully!");
        } else {
            System.out.println("\n Failed to create user account. Email might already exist.");
        }
    }
    
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}