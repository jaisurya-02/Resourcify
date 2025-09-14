# Resourcify

A Java-based resource management system for educational institutions.

## Project Structure

```
resourcify/
├── src/
│   └── com/
│       └── resourcify/
│           ├── ResourcifyApp.java          (Main Application)
│           ├── database/
│           │   └── DatabaseManager.java    (JDBC Connection & Operations)
│           ├── model/
│           │   ├── User.java              (Abstract Base Class)
│           │   ├── Student.java           (Student Implementation)
│           │   ├── Faculty.java           (Faculty Implementation)
│           │   ├── Admin.java             (Admin Implementation)
│           │   ├── Resource.java          (Resource Model)
│           │   └── Booking.java           (Booking Model)
│           └── ui/
│               └── ConsoleUI.java         (Console Interface)
├── database/
│   └── resourcify_db.sql                 (Database Schema)
├── lib/
│   └── mysql-connector-java.jar          (MySQL JDBC Driver)
└── Resourcify.jar                        (Compiled JAR file)
```

## Setup Instructions

1. Create the database using the SQL script in `database/resourcify_db.sql`
2. Make sure you have the MySQL JDBC driver in the `lib` folder
3. Configure the database connection in `DatabaseManager.java`
4. Compile the project and run `ResourcifyApp`

## Features

- User Management (Students, Faculty, Admins)
- Resource Management
- Booking System
- Console-based User Interface

## Requirements

- Java 8 or higher
- MySQL Server
- MySQL JDBC Driver
