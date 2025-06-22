package com.example.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.sql.*;

public class Users extends Observable {
    private User currentUser;
    private List<User> userList = new ArrayList<>();

    public Users() {}

    public void authenticate(String username, String password) {
        clearData();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                Wrapper wrapper = Wrapper.getInstance();
                wrapper.loadUsers(rs); // загружаем пользователей через обертку
                userList = wrapper.getUsers(); // получаем список обратно

                if (!userList.isEmpty()) {
                    currentUser = userList.get(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при работе с базой данных");
        }

        setChanged();
        notifyObservers();
    }
    public User registerUser(String username, String password, String role, int workerId) {
        String sql = "INSERT INTO users (username, password, role, worker_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setInt(4, workerId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new User(id, username, password, role, workerId);
                    }
                }
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя");
            return null;
        }
    }

    private void clearData() {
        userList.clear();
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;
        setChanged();
        notifyObservers();
    }
}