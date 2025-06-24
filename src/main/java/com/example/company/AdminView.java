package com.example.company;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminView {
    private Stage primaryStage;
    private Button changePasswordBtn;
    private Button changeUsernameBtn;
    private Button createExtraWorkBtn;
    private Button createTypeWorkBtn;
    private Button createWorkerBtn;
    private Button createParticipationBtn;

    public AdminView(Stage primaryStage, int workerId) {
        this.primaryStage = primaryStage;
        this.changePasswordBtn = new Button("Сменить пароль");
        this.changeUsernameBtn = new Button("Сменить логин");
        this.createExtraWorkBtn = new Button("Создать дополнительную работу");
        this.createTypeWorkBtn = new Button("Создать тип работы");
        this.createWorkerBtn = new Button("Добавить сотрудника");
        this.createParticipationBtn = new Button("Назначить работу");
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.getChildren().addAll(changePasswordBtn, changeUsernameBtn, createExtraWorkBtn, createTypeWorkBtn, createWorkerBtn, createParticipationBtn);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Администратор");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Button getChangePasswordBtn() {
        return changePasswordBtn;
    }
    public Button getChangeUsernameBtn() {
        return changeUsernameBtn;
    }
    public Button getCreateExtraWorkBtn() {
        return createExtraWorkBtn;
    }
    public Button getCreateTypeWorkBtn() {
        return createTypeWorkBtn;
    }
    public Button getCreateWorkerBtn() {
        return createWorkerBtn;
    }
    public Button getCreateParticipationBtn() {
        return createParticipationBtn;
    }
}