package com.example.company;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkerView {
    private Stage primaryStage;
    private Button changePasswordBtn;
    private Button changeUsernameBtn;
    private Button createExtraWorkBtn;

    public WorkerView(Stage primaryStage, int workerId) {
        this.primaryStage = primaryStage;
        this.changePasswordBtn = new Button("Сменить пароль");
        this.changeUsernameBtn = new Button("Сменить логин");
        this.createExtraWorkBtn = new Button("Создать дополнительную работу");
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.getChildren().addAll(changePasswordBtn, changeUsernameBtn, createExtraWorkBtn);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Сотрудник");
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
}