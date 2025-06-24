package com.example.company;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class WorkerView {
    private Stage primaryStage;
    private Button changePasswordBtn;
    private Button changeUsernameBtn;
    private Button createExtraWorkBtn;
    private TableView<Participation> participationsTable;
    private Label participationsLabel;
    private Label noParticipationsLabel;
    private VBox participationsContainer;

    public WorkerView(Stage primaryStage, int workerId) {
        this.primaryStage = primaryStage;
        this.changePasswordBtn = new Button("Сменить пароль");
        this.changeUsernameBtn = new Button("Сменить логин");
        this.createExtraWorkBtn = new Button("Создать дополнительную работу");
        this.participationsLabel = new Label("Мои дополнительные работы:");
        this.participationsTable = new TableView<>();
        this.noParticipationsLabel = new Label("Вы не участвуете в дополнительных работах");

        noParticipationsLabel.setVisible(false);
        this.participationsContainer = new VBox(5);
        participationsContainer.getChildren().addAll(participationsLabel, noParticipationsLabel, participationsTable);
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.getChildren().addAll(changePasswordBtn, changeUsernameBtn, createExtraWorkBtn, participationsContainer);

        Scene scene = new Scene(layout, 500, 400);
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
    public TableView<Participation> getParticipationsTable() {
        return participationsTable;
    }
    public Label getNoParticipationsLabel() {
        return noParticipationsLabel;
    }
}