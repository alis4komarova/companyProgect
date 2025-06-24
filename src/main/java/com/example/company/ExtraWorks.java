package com.example.company;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.sql.*;

public class ExtraWorks extends Observable {
    private List<ExtraWork> extraWorkList = new ArrayList<>();

    public ExtraWorks() {}

    public List<ExtraWork> getExtraWorkList() {
        return extraWorkList;
    }

    public void setExtraWorkList(List<ExtraWork> extraWorkList) {
        this.extraWorkList = extraWorkList;
        setChanged();
        notifyObservers();
    }

    public boolean createExtraWork(LocalDate dateStart, String urgency, int workerId, int typeId) {
        String sql = "INSERT INTO extra_works (date_start, urgency, worker_id, type_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(dateStart));
            stmt.setString(2, urgency);
            stmt.setInt(3, workerId);
            stmt.setInt(4, typeId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ExtraWork newWork = new ExtraWork(
                                generatedKeys.getInt(1),
                                dateStart,
                                urgency,
                                workerId,
                                typeId
                        );

                        List<ExtraWork> currentWorks = new ArrayList<>(getExtraWorkList());
                        currentWorks.add(newWork);
                        setExtraWorkList(currentWorks);
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Ошибка при создании доп. работы: " + e.getMessage());
            return false;
        }
    }

    public List<TypeWork> getAvailableTypeWorks() {
        String sql = "SELECT * FROM type_work";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Wrapper wrapper = Wrapper.getInstance();
            wrapper.loadTypeWorks(rs);
            return wrapper.getTypeWorks();

        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке типов работ: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public boolean updateExtraWorkDetails(int workId, LocalDate dateEnd, double timeHours) {
        String sql = "UPDATE extra_works SET date_end = ?, time_hours = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, dateEnd != null ? Date.valueOf(dateEnd) : null);
            stmt.setDouble(2, timeHours);
            stmt.setInt(3, workId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                for (ExtraWork work : extraWorkList) {
                    if (work.getId() == workId) {
                        work = new ExtraWork(
                                work.getDateStart(),
                                dateEnd,
                                work.getUrgency(),
                                timeHours,
                                work.getWorkerId(),
                                work.getTypeId()
                        );
                        setExtraWorkList(new ArrayList<>(extraWorkList));
                        break;
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении доп. работы: " + e.getMessage());
            return false;
        }
    }
    public List<ExtraWork> getUnfinishedExtraWorks() {
        String sql = "SELECT * FROM extra_works WHERE date_end IS NULL";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Wrapper wrapper = Wrapper.getInstance();
            wrapper.loadExtraWorks(rs);
            return wrapper.getExtraWorks();

        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке незавершенных работ: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public boolean deleteExtraWork(int workId) {
        String sql = "DELETE FROM extra_works WHERE id = ? AND date_end IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // обновляем список работ после удаления
                List<ExtraWork> updatedWorks = new ArrayList<>();
                for (ExtraWork work : extraWorkList) {
                    if (work.getId() != workId) {
                        updatedWorks.add(work);
                    }
                }
                setExtraWorkList(updatedWorks);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении работы: " + e.getMessage());
            return false;
        }
    }
}