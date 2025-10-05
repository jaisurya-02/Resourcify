package com.resourcify.model;

public abstract class User {
    protected int userId;
    protected String name;
    protected String email;
    protected String password;
    
    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    // Getters
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    
    // Abstract methods
    public abstract boolean login(String email, String password);
    public abstract void logout();
    public abstract String getRole();
    
    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", role='" + getRole() + '\'' +
               '}';
    }
}