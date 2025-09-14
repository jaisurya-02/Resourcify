package com.resourcify.ui;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to Resourcify ===");
        showMainMenu();
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    System.out.println("Thank you for using Resourcify!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleLogin() {
        // TODO: Implement login logic
        System.out.println("Login functionality to be implemented");
    }
}
