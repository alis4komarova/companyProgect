package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.sql.*;

public class TypeWorks extends Observable {
    private List<TypeWork> typeWorkList = new ArrayList<>();

    public TypeWorks() {}

    public List<TypeWork> getTypeWorkList() {
        return typeWorkList;
    }

    public void setTypeWorkList(List<TypeWork> typeWorkList) {
        this.typeWorkList = typeWorkList;
        setChanged();
        notifyObservers();
    }
    public boolean createTypeWork(double payment, double hours, int countPeople) {
        String sql = "INSERT INTO type_work (payment, long_hours, count_people) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, payment);
            stmt.setDouble(2, hours);
            stmt.setInt(3, countPeople);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при создании типа работы: " + e.getMessage());
            return false;
        }
    }
}