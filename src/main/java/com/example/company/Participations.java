package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.sql.*;

public class Participations extends Observable {
    private List<Participation> participationList = new ArrayList<>();

    public Participations() {}

    public List<Participation> getParticipationList() {
        return participationList;
    }

    public void setParticipationList(List<Participation> participationList) {
        this.participationList = participationList;
        setChanged();
        notifyObservers();
    }
    public void loadParticipationsByWorkerId(int workerId) {
        String sql = "SELECT * FROM participation WHERE worker_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workerId);
            try (ResultSet rs = stmt.executeQuery()) {
                Wrapper wrapper = Wrapper.getInstance();
                wrapper.loadParticipations(rs);
                setParticipationList(wrapper.getParticipations());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке участий: " + e.getMessage());
        }
    }
}