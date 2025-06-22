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

    private void clearData() {
        userList.clear();
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}