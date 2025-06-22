package com.example.company;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterView {
    private Users model;
    private Stage primaryStage;

    public RegisterView(Stage primaryStage, Users model) {
        this.primaryStage = primaryStage;
        this.model = model;
    }
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Пользователь зарегистрирован");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Ошибка регистрации");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}