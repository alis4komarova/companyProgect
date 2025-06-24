package com.example.company;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class WorkerController {
    private WorkerView view;
    private Users model;
    private int userId;
    private ExtraWorks extraWorksModel;
    private Participations participationsModel;
    private Workers workersModel;

    public WorkerController(Stage primaryStage, Users model, WorkerView view, int userId) {
        this.view = view;
        this.model = model;
        this.userId = userId;
        this.extraWorksModel = new ExtraWorks();
        this.participationsModel = new Participations();
        this.workersModel = new Workers();
        setupEvents();
    }

    private void setupEvents() {
        view.getChangePasswordBtn().setOnAction(e -> showChangePasswordDialog());
        view.getChangeUsernameBtn().setOnAction(e -> showChangeUsernameDialog());
        view.getChangeQualificationBtn().setOnAction(e -> showChangeQualificationDialog());
        view.getCreateExtraWorkBtn().setOnAction(e -> showCreateExtraWorkDialog());
        setupParticipationsTable();
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

    private void showCreateExtraWorkDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        DatePicker dateStartPicker = new DatePicker();
        ComboBox<String> urgencyCombo = new ComboBox<>();
        urgencyCombo.getItems().addAll(
                ExtraWork.URGENCY_LOW,
                ExtraWork.URGENCY_MEDIUM,
                ExtraWork.URGENCY_HIGH
        );

        ComboBox<TypeWork> typeWorkCombo = new ComboBox<>();
        List<TypeWork> typeWorks = extraWorksModel.getAvailableTypeWorks();
        typeWorkCombo.setItems(FXCollections.observableArrayList(typeWorks));
        typeWorkCombo.setConverter(new StringConverter<TypeWork>() {
                @Override
                public String toString(TypeWork typeWork) {
                    if (typeWork == null) {
                        return ""; // или "Выберите тип работы"
                    }
                    return "Тип" + typeWork.getId() + " (Оплата: " + typeWork.getPayment() + ")";
                }

                @Override
                public TypeWork fromString(String string) {
                    return null;
                }
            });

        TextField workerIdField = new TextField();
        Button createBtn = new Button("Создать");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("Дата начала:"), 0, 0);
        grid.add(dateStartPicker, 1, 0);
        grid.add(new Label("Срочность:"), 0, 1);
        grid.add(urgencyCombo, 1, 1);
        grid.add(new Label("Тип работы:"), 0, 2);
        grid.add(typeWorkCombo, 1, 2);
        grid.add(new Label("ID ответственного:"), 0, 4);
        grid.add(workerIdField, 1, 4);
        grid.add(createBtn, 0, 5);
        grid.add(cancelBtn, 1, 5);

        createBtn.setOnAction(event -> {
            try {
                LocalDate dateStart = dateStartPicker.getValue();
                String urgency = urgencyCombo.getValue();
                int workerId = Integer.parseInt(workerIdField.getText());
                TypeWork selectedType = typeWorkCombo.getValue();

                if (selectedType == null) {
                    showAlert(dialogStage, "Ошибка", "Выберите тип работы", Alert.AlertType.ERROR);
                    return;
                }
                if (extraWorksModel.createExtraWork(dateStart, urgency, workerId, selectedType.getId())) {
                    showAlert(dialogStage, "Получилось", "Ура! Дополнительная работа создана", Alert.AlertType.INFORMATION);
                    loadParticipations();
                    dialogStage.close();
                } else {
                    showAlert(dialogStage, "Ошибка", "Не удалось создать работу", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert(dialogStage, "Ошибка", "Не удалось создать работу", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 400, 300);
        dialogStage.setTitle("Создать дополнительную работу");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void setupParticipationsTable() {
        TableColumn<Participation, Integer> workIdCol = new TableColumn<>("ID доп. работы");
        workIdCol.setCellValueFactory(new PropertyValueFactory<>("extraWorkId"));

        TableColumn<Participation, Double> salaryCol = new TableColumn<>("Доплата");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("plusSalary"));

        //новые
        view.getParticipationsTable().getColumns().clear();
        view.getParticipationsTable().getColumns().addAll(workIdCol, salaryCol);

        view.getParticipationsTable().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //автоматическое растягивание столбцов
        view.getParticipationsTable().setVisible(true);
        loadParticipations();
    }
    private void loadParticipations() {
        User currentUser = model.getCurrentUser();
        if (currentUser != null && currentUser.getWorkerId() > 0) {
            participationsModel.loadParticipationsByWorkerId(currentUser.getWorkerId());
            List<Participation> participations = participationsModel.getParticipationList();

            if (participations.isEmpty()) {
                view.getNoParticipationsLabel().setVisible(true);
                view.getParticipationsTable().setVisible(false);
            } else {
                view.getNoParticipationsLabel().setVisible(false);
                view.getParticipationsTable().setVisible(true);
                view.getParticipationsTable().setItems(FXCollections.observableArrayList(participations));
            }
        }
    }
    private void showChangeQualificationDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField newQualificationField = new TextField();
        Button changeBtn = new Button("Изменить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("Квалификация:"), 0, 0);
        grid.add(newQualificationField, 1, 0);
        grid.add(changeBtn, 0, 1);
        grid.add(cancelBtn, 1, 1);

        changeBtn.setOnAction(event -> {
            String newQualification = newQualificationField.getText();
            if (newQualification.isEmpty()) {
                showAlert(dialogStage, "Ошибка", "Квалификация не может быть пустой", Alert.AlertType.ERROR);
                return;
            }

            User currentUser = model.getCurrentUser();
            if (currentUser != null && workersModel.updateQualification(currentUser.getWorkerId(), newQualification)) {
                showAlert(dialogStage, "Получилось", "Ура! Квалификация обновлена", Alert.AlertType.INFORMATION);
                dialogStage.close();
            } else {
                showAlert(dialogStage, "Ошибка", "Не удалось изменить квалификацию", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 300, 150);
        dialogStage.setTitle("Обновить квалификацию");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}