package com.example.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wrapper {
    private static Wrapper instance; //синглтон
    private List<User> users;

    private Wrapper() {
        users = new ArrayList<>();
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
}