package com.example.company;

public class Participation {
    private int workerId;
    private int extraWorkId;
    private double plusSalary;

    public Participation() {
    }

    public Participation(int workerId, int extraWorkId, double plusSalary) {
        this.workerId = workerId;
        this.extraWorkId = extraWorkId;
        this.plusSalary = plusSalary;
    }

    public int getWorkerId() { return workerId; }
    public int getExtraWorkId() { return extraWorkId; }
    public double getPlusSalary() { return plusSalary; }
}