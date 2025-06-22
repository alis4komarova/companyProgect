package com.example.company;

public class Worker {
    private int id;
    private String lastName;
    private String firstName;
    private String secondName;
    private String post;
    private String qualification;
    private double salary;

    public Worker(){}

    public Worker(String lastName, String firstName, String secondName, String post, String qualification, double salary){
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.post = post;
        this.qualification = qualification;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getSecondName() { return secondName; }
    public String getPost() { return post; }
    public String getQualification() { return qualification; }
    public double getSalary() { return salary; }
}