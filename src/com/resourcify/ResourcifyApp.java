package com.resourcify;

import com.resourcify.database.DatabaseManager;
import com.resourcify.model.*;
import com.resourcify.ui.ConsoleUI;

import java.util.Scanner;

public class ResourcifyApp {
    private static DatabaseManager dbManager;
    private static ConsoleUI ui;
    private static User currentUser;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("     WELCOME TO RESOURCIFY");
        System.out.println("   Campus Resource Booking System");
        System.out.println("=".repeat(50));
        
        // Initialize components
        dbManager = DatabaseManager.getInstance();
        ui = new ConsoleUI();
        scanner = new Scanner(System.in);
        
        // Check database connection
        if (dbManager.getConnection() == null) {
            System.err.println("Failed to connect to database. Please check your MySQL configuration.");
            System.err.println("Make sure MySQL is running and update the connection details in DatabaseManager.java");
            return;
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
                int