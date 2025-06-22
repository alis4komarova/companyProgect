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
        if (user != null && User.ROLE_ADMIN.equals(user.getRole())) {
            new AdminView(primaryStage).show();
        } else if (user != null) {
            new WorkerView(primaryStage, user.getWorkerId()).show();
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