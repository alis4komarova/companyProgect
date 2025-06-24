package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.sql.*;

public class Workers extends Observable {
    private List<Worker> workerList = new ArrayList<>();
    private Worker currentWorker;

    public Workers() {}

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
        setChanged();
        notifyObservers();
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker worker) {
        this.currentWorker = worker;
        setChanged();
        notifyObservers();
    }
    public boolean updateQualification(int workerId, String newQualification) {
        String sql = "UPDATE workers SET qualification = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newQualification);
            stmt.setInt(2, workerId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Ошибка при изменении квалификации: " + e.getMessage());
            return false;
        }
    }
    public boolean createWorker(String lastName, String firstName, String secondName,
                                 String post, String qualification, double salary) {
        String sql = "INSERT INTO workers (last_name, first_name, second_name, post, qualification, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            stmt.setString(3, secondName);
            stmt.setString(4, post);
            stmt.setString(5, qualification);
            stmt.setDouble(6, salary);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при создании сотрудника: " + e.getMessage());
            return false;
        }
    }
}