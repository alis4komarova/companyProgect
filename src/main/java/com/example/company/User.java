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

    public User(String username, String password, String role, int workerId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.workerId = workerId;
    }
}