package com.resourcify.model;

public class Faculty extends User {
    private String facultyId;
    private String department;

    public Faculty(int id, String name, String email, String password, String facultyId, String department) {
        super(id, name, email, password);
        this.facultyId = facultyId;
        this.department = department;
    }

    @Override
    public String getRole() {
        return "FACULTY";
    }

    // Getters
    public String getFacultyId() { return facultyId; }
    public String getDepartment() { return department; }
}
