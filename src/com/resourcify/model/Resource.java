package com.resourcify.model;

public class Resource {
    private int id;
    private String name;
    private String type;
    private String status;
    private String location;

    public Resource(int id, String name, String type, String status, String location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.location = location;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getLocation() { return location; }
    public void setStatus(String status) { this.status = status; }
}
