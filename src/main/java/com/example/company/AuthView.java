package com.example.company;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class AuthView {
    private Stage primaryStage;
    private Users model;

    public AuthView(Stage primaryStage, Users model) {
        this.primaryStage = primaryStage;
        this.model = model;
    }

    public void showView() {
        User user = model.getCurrentUser();
        if (user != null) {
            primaryStage.close(); // закрываем текущее окно авторизации

            Stage newStage = new Stage();
            if (User.ROLE_ADMIN.equals(user.getRole())) {
                new AdminView(newStage, user.getWorkerId()).show();
            } else {
                new WorkerView(newStage, user.getWorkerId()).show();
            }
        } else {
            showAlert("Ошибка: пользователь не найден");
        }
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка авторизации");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}