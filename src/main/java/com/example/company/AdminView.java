package com.example.company;

import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button updateExtraWorkBtn;
    private TableView<ExtraWork> extraWorksTable;
    private Label extraWorksLabel;
    private Label noExtraWorksLabel;
    private VBox extraWorksContainer;
    private Button deleteExtraWorkBtn;

    public AdminView(Stage primaryStage, int workerId) {
        this.primaryStage = primaryStage;
        this.changePasswordBtn = new Button("Сменить пароль");
        this.changeUsernameBtn = new Button("Сменить логин");
        this.createExtraWorkBtn = new Button("Создать дополнительную работу");
        this.createTypeWorkBtn = new Button("Создать тип работы");
        this.createWorkerBtn = new Button("Добавить сотрудника");
        this.createParticipationBtn = new Button("Назначить работу");
        this.updateExtraWorkBtn = new Button("Обновить дополнительную работу");
        this.deleteExtraWorkBtn = new Button("Удалить дополнительную работу");

        this.extraWorksLabel = new Label("Незавершенные дополнительные работы");
        this.extraWorksTable = new TableView<>();
        this.noExtraWorksLabel = new Label("Все дополнительные работы завершены");
        noExtraWorksLabel.setVisible(false);

        this.extraWorksContainer = new VBox(5);
        extraWorksContainer.getChildren().addAll(extraWorksLabel, noExtraWorksLabel, extraWorksTable);
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.getChildren().addAll(changePasswordBtn, changeUsernameBtn, createExtraWorkBtn, createTypeWorkBtn, createWorkerBtn, createParticipationBtn, updateExtraWorkBtn, deleteExtraWorkBtn, extraWorksContainer);

        Scene scene = new Scene(layout, 400, 500);
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
    public Button getUpdateExtraWorkBtn() {
        return updateExtraWorkBtn;
    }
    public TableView<ExtraWork> getExtraWorksTable() {
        return extraWorksTable;
    }
    public Label getNoExtraWorksLabel() {
        return noExtraWorksLabel;
    }
    public Button getDeleteExtraWorkBtn() {
        return deleteExtraWorkBtn;
    }
}