package com.example.company;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private Integer workerId;

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_WORKER = "worker";

    public User() {}

    public User(int id, String username, String password, String role, int workerId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.workerId = workerId;
    }
    public String getRole() { return role; }
    public int getWorkerId() { return workerId; }
    public int getId(){ return id;}
}