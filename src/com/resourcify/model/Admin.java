package com.resourcify.model;

public class Admin extends User {
    private String adminId;

    public Admin(int id, String name, String email, String password, String adminId) {
        super(id, name, email, password);
        this.adminId = adminId;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    // Getter
    public String getAdminId() { return adminId; }
}
