package com.example.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wrapper {
    private static Wrapper instance; //синглтон
    private List<User> users;
    private List<ExtraWork> extraWorks;
    private List<TypeWork> typeWorks;
    private List<Participation> participations;

    private Wrapper() {
        users = new ArrayList<>();
        extraWorks = new ArrayList<>();
        typeWorks = new ArrayList<>();
        participations = new ArrayList<>();
    }

    public static Wrapper getInstance() {
        if (instance == null) {
            instance = new Wrapper();
        }
        return instance;
    }

    public void loadUsers(ResultSet rs) throws SQLException {
        users.clear();
        while (rs.next()) {
            users.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getInt("worker_id")
            ));
        }
    }

    public List<User> getUsers() {
        return users;
    }
    public void loadTypeWorks(ResultSet rs) throws SQLException {
        typeWorks.clear();
        while (rs.next()) {
            typeWorks.add(new TypeWork(
                    rs.getInt("id"),
                    rs.getDouble("payment"),
                    rs.getDouble("long_hours"),
                    rs.getInt("count_people")
            ));
        }
    }
    public List<TypeWork> getTypeWorks() {
        return typeWorks;
    }
    public void loadParticipations(ResultSet rs) throws SQLException {
        participations.clear();
        while (rs.next()) {
            participations.add(new Participation(
                    rs.getInt("worker_id"),
                    rs.getInt("extra_work_id"),
                    rs.getDouble("plus_salary")
            ));
        }
    }

    public List<Participation> getParticipations() {
        return participations;
    }
}