package com.example.company;

public class TypeWork {
    private int id;
    private double payment;
    private double longHours;
    private int countPeople;

    public TypeWork() {}

    public TypeWork(double payment, double longHours, int countPeople) {
        this.payment = payment;
        this.longHours = longHours;
        this.countPeople = countPeople;
    }
    public TypeWork(int id, double payment, double longHours, int countPeople) {
        this.id = id;
        this.payment = payment;
        this.longHours = longHours;
        this.countPeople = countPeople;
    }
    public int getId() { return id; }
    public double getPayment() { return payment; }

}