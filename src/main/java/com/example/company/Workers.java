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
}