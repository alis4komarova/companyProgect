package com.example.company;

import javafx.geometry.*; // для отступов
import javafx.scene.Scene;  // сцена JavaFX
import javafx.scene.control.*;
import javafx.scene.layout.*; // сетка для элементов и для коробки
import javafx.stage.Stage;  // окно
import java.util.Observable;
import java.util.Observer;

public class AuthController implements Observer {
    private Users model;
    private AuthView view;
    private Stage primaryStage;

    public AuthController(Stage primaryStage, Users model, AuthView view) {
        this.primaryStage = primaryStage;
        this.model = model;
        this.view = view;
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20)); //отступы все
        grid.setVgap(10); // верт
        grid.setHgap(10); // гориз

        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        Button loginBtn = new Button("Вход");
        Button registerBtn = new Button("Регистрация");

        grid.add(new Label("Логин:"), 0, 0); // в 0 столб 0 стр
        grid.add(userField, 1, 0);
        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginBtn, 0, 2);
        grid.add(registerBtn, 1, 2);

        HBox buttonBox = new HBox(10); // горизонтальный контейнер с отступом 10
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER); // выравнивание по центру
        buttonBox.getChildren().addAll(loginBtn, registerBtn);
        grid.add(buttonBox, 0, 2, 2, 1);

        // обработчик событий кнопочки авт
        loginBtn.setOnAction(e -> {authenticate(
                    userField.getText(),
                    passField.getText()
            );});

        // обработчик событий кнопочки рег
        registerBtn.setOnAction(e -> {
            RegisterView registerView = new RegisterView(primaryStage, model);
            RegisterController registerController = new RegisterController(primaryStage, model, registerView);
            registerController.show(); // показываем окно регистрации
        });

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(scene); //установка сцены
        primaryStage.show(); //показать окошко
    }

    public void authenticate(String username, String password) {
        model.authenticate(username, password);
    }

    @Override
    public void update(Observable o, Object arg) {
        User user = model.getCurrentUser();
        if (user != null) {
            view.showView();
        } else {
            view.showAlert("Неверный логин или пароль");
        }
    }
}