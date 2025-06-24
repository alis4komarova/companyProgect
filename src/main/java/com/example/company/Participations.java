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
    public boolean deleteParticipation(int workerId, int extraWorkId) {
        String sql = "DELETE FROM participation WHERE worker_id = ? AND extra_work_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workerId);
            stmt.setInt(2, extraWorkId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                // обновляем список после удаления
                loadParticipationsByWorkerId(workerId);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении участия: " + e.getMessage());
            return false;
        }
    }
}