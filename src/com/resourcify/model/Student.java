package com.resourcify.model;

public class Student extends User {
    private String studentId;
    private String department;

    public Student(int id, String name, String email, String password, String studentId, String department) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.department = department;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getDepartment() { return department; }
}
