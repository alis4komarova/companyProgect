package com.example.company;

import java.time.*;
public class ExtraWork {
    private int id;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String urgency;
    private double timeHours;
    private int workerId;
    private int typeId;

    public static final String URGENCY_LOW = "Низкая";
    public static final String URGENCY_MEDIUM = "Средняя";
    public static final String URGENCY_HIGH = "Высокая";

    public ExtraWork(){}
    public ExtraWork(int id, LocalDate dateStart, String urgency, int workerId, int typeId) {
        this.id = id;
        this.dateStart = dateStart;
        this.urgency = urgency;
        this.workerId = workerId;
        this.typeId = typeId;
    }

    public ExtraWork(LocalDate dateStart, LocalDate dateEnd, String urgency, double timeHours, int workerId, int typeId) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.urgency = urgency;
        this.timeHours = timeHours;
        this.workerId = workerId;
        this.typeId = typeId;
    }

    public int getId() { return id; }
    public LocalDate getDateStart() { return dateStart; }
    public LocalDate getDateEnd() { return dateEnd; }
    public String getUrgency() { return urgency; }
    public double getTimeHours() { return timeHours; }
    public int getWorkerId() { return workerId; }
    public int getTypeId() { return typeId; }
    public void setId(int id) { this.id = id; }
    public void setDateEnd(LocalDate dateEnd) { this.dateEnd = dateEnd; }
    public void setTimeHours(double timeHours) { this.timeHours = timeHours; }
    public void setWorkerId(int workerId) { this.workerId = workerId; }
}