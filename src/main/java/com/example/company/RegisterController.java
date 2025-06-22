package com.example.company;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class RegisterController {
    private Users model;
    private RegisterView view;
    private Stage primaryStage;

    public RegisterController(Stage primaryStage, Users model, RegisterView view) {
        this.primaryStage = primaryStage;
        this.model = model;
        this.view = view;
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "worker");
        roleBox.setValue("worker");

        TextField workerIdField = new TextField();

        Button registerBtn = new Button("Зарегистрироваться");
        Button backBtn = new Button("Назад");

        grid.add(new Label("Логин:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Роль:"), 0, 2);
        grid.add(roleBox, 1, 2);
        grid.add(new Label("ID сотрудника:"), 0, 3);
        grid.add(workerIdField, 1, 3);
        grid.add(registerBtn, 1, 4);
        grid.add(backBtn, 1, 5);

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();
            int workerId;

            try {
                workerId = Integer.parseInt(workerIdField.getText());
            } catch (NumberFormatException ex) {
                view.showError("ID сотрудника должен быть числом");
                return;
            }

            User registeredUser = model.registerUser(username, password, role, workerId);

            if (registeredUser != null) {
                model.setCurrentUser(registeredUser);
                view.showAlert("Ура! Вы зарегистрировались!");
                primaryStage.close();
            } else {
                view.showError("Ошибка при регистрации пользователя.");
            }
        });

        backBtn.setOnAction(e -> {
            AuthView authView = new AuthView(primaryStage, model);
            AuthController controller = new AuthController(primaryStage, model, authView);
            controller.show();
        });

        Scene scene = new Scene(grid, 350, 250);
        primaryStage.setTitle("Регистрация");
        primaryStage.setScene(scene);
    }
}