package com.example.company;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WorkerController {
    private WorkerView view;
    private Users model;
    private int userId;

    public WorkerController(Stage primaryStage, Users model, WorkerView view, int userId) {
        this.view = view;
        this.model = model;
        this.userId = userId;
        setupEvents();
    }

    private void setupEvents() {
        view.getChangePasswordBtn().setOnAction(e -> showChangePasswordDialog());
        view.getChangeUsernameBtn().setOnAction(e -> showChangeUsernameDialog());
    }

    private void showChangePasswordDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        PasswordField oldPasswordField = new PasswordField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button changeBtn = new Button("Изменить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("Старый пароль:"), 0, 0);
        grid.add(oldPasswordField, 1, 0);
        grid.add(new Label("Новый пароль:"), 0, 1);
        grid.add(newPasswordField, 1, 1);
        grid.add(new Label("Подтвердите:"), 0, 2);
        grid.add(confirmPasswordField, 1, 2);
        grid.add(changeBtn, 0, 3);
        grid.add(cancelBtn, 1, 3);

        changeBtn.setOnAction(event -> {
            if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
                showAlert(dialogStage, "Ошибка", "Пароли не совпадают", Alert.AlertType.ERROR);
                return;
            }

            if (model.updatePassword(userId, newPasswordField.getText())) {
                showAlert(dialogStage, "Получилось", "Ура! Пароль изменен", Alert.AlertType.INFORMATION);
                dialogStage.close();
            } else {
                showAlert(dialogStage, "Ошибка", "Не удалось изменить пароль", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 300, 200);
        dialogStage.setTitle("Смена пароля");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void showAlert(Stage owner, String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showChangeUsernameDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField newUsernameField = new TextField();
        Button changeBtn = new Button("Изменить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("Новый логин:"), 0, 0);
        grid.add(newUsernameField, 1, 0);
        grid.add(changeBtn, 0, 1);
        grid.add(cancelBtn, 1, 1);

        changeBtn.setOnAction(event -> {
            String newUsername = newUsernameField.getText();
            if (newUsername.isEmpty()) {
                showAlert(dialogStage, "Ошибка", "Логин не может быть пустым", Alert.AlertType.ERROR);
                return;
            }

            if (model.updateUsername(userId, newUsername)) {
                showAlert(dialogStage, "Получилось", "Ура! Логин изменен", Alert.AlertType.INFORMATION);
                dialogStage.close();
            } else {
                showAlert(dialogStage, "Ошибка", "Не удалось изменить логин", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 300, 150);
        dialogStage.setTitle("Смена логина");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}