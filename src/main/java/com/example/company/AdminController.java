package com.example.company;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

public class AdminController {
    private AdminView view;
    private Users model;
    private int userId;
    private ExtraWorks extraWorksModel;
    private TypeWorks typeWorksModel;
    private Workers workersModel;
    private Participations participationsModel;

    public AdminController(Stage primaryStage, Users model, AdminView view, int userId) {
        this.view = view;
        this.model = model;
        this.userId = userId;
        this.extraWorksModel = new ExtraWorks();
        this.typeWorksModel = new TypeWorks();
        this.workersModel = new Workers();
        this.participationsModel = new Participations();
        setupEvents();
    }

    private void setupEvents() {
        view.getChangePasswordBtn().setOnAction(e -> showChangePasswordDialog());
        view.getChangeUsernameBtn().setOnAction(e -> showChangeUsernameDialog());
        view.getCreateExtraWorkBtn().setOnAction(e -> showCreateExtraWorkDialog());
        view.getCreateTypeWorkBtn().setOnAction(e -> showCreateTypeWorkDialog());
        view.getCreateWorkerBtn().setOnAction(e -> showCreateWorkerDialog());
        view.getCreateParticipationBtn().setOnAction(e -> showCreateParticipationDialog());
        view.getUpdateExtraWorkBtn().setOnAction(e -> showUpdateExtraWorkDialog());
        view.getDeleteExtraWorkBtn().setOnAction(e -> showDeleteExtraWorkDialog());

        setupExtraWorksTable();
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


    private void showAlert(Stage owner, String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
                    return "Выберите тип работы";
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
    private void showCreateTypeWorkDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField paymentField = new TextField();
        TextField hoursField = new TextField();
        TextField countPeopleField = new TextField();
        Button createBtn = new Button("Создать");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("Оплата:"), 0, 0);
        grid.add(paymentField, 1, 0);
        grid.add(new Label("Часы:"), 0, 1);
        grid.add(hoursField, 1, 1);
        grid.add(new Label("Кол-во людей:"), 0, 2);
        grid.add(countPeopleField, 1, 2);
        grid.add(createBtn, 0, 3);
        grid.add(cancelBtn, 1, 3);

        createBtn.setOnAction(event -> {
            try {
                double payment = Double.parseDouble(paymentField.getText());
                double hours = Double.parseDouble(hoursField.getText());
                int countPeople = Integer.parseInt(countPeopleField.getText());

                if (typeWorksModel.createTypeWork(payment, hours, countPeople)) {
                    showAlert(dialogStage, "Получилось", "Ура! Тип работы создан", Alert.AlertType.INFORMATION);
                    dialogStage.close();
                } else {
                    showAlert(dialogStage, "Ошибка", "Не удалось создать тип работы", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert(dialogStage, "Ошибка", "Некорректные данные", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 300, 200);
        dialogStage.setTitle("Создать тип работы");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
    private void showCreateWorkerDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField lastNameField = new TextField();
        TextField firstNameField = new TextField();
        TextField secondNameField = new TextField();
        TextField postField = new TextField();
        TextField qualificationField = new TextField();
        TextField salaryField = new TextField();
        Button createBtn = new Button("Добавить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("Фамилия:"), 0, 0);
        grid.add(lastNameField, 1, 0);
        grid.add(new Label("Имя:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Отчество:"), 0, 2);
        grid.add(secondNameField, 1, 2);
        grid.add(new Label("Должность:"), 0, 3);
        grid.add(postField, 1, 3);
        grid.add(new Label("Квалификация:"), 0, 4);
        grid.add(qualificationField, 1, 4);
        grid.add(new Label("Зарплата:"), 0, 5);
        grid.add(salaryField, 1, 5);
        grid.add(createBtn, 0, 6);
        grid.add(cancelBtn, 1, 6);

        createBtn.setOnAction(event -> {
            try {
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String secondName = secondNameField.getText();
                String post = postField.getText();
                String qualification = qualificationField.getText();
                double salary = Double.parseDouble(salaryField.getText());

                if (workersModel.createWorker(lastName, firstName, secondName, post, qualification, salary)) {
                    showAlert(dialogStage, "Получилось", "Ура! Сотрудник добавлен", Alert.AlertType.INFORMATION);
                    dialogStage.close();
                } else {
                    showAlert(dialogStage, "Ошибка", "Не удалось добавить сотрудника", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert(dialogStage, "Ошибка", "Некорректная зарплата", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 400, 300);
        dialogStage.setTitle("Добавить сотрудника");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
    private void showCreateParticipationDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField workerIdField = new TextField();
        TextField workIdField = new TextField();
        TextField salaryField = new TextField();
        Button createBtn = new Button("Назначить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("ID работника:"), 0, 0);
        grid.add(workerIdField, 1, 0);
        grid.add(new Label("ID работы:"), 0, 1);
        grid.add(workIdField, 1, 1);
        grid.add(new Label("Доплата:"), 0, 2);
        grid.add(salaryField, 1, 2);
        grid.add(createBtn, 0, 3);
        grid.add(cancelBtn, 1, 3);

        createBtn.setOnAction(event -> {
            try {
                int workerId = Integer.parseInt(workerIdField.getText());
                int workId = Integer.parseInt(workIdField.getText());
                double plusSalary = Double.parseDouble(salaryField.getText());

                if (participationsModel.createParticipation(workerId, workId, plusSalary)) {
                    showAlert(dialogStage, "Получилось", "Ура! Работа назначена", Alert.AlertType.INFORMATION);
                    dialogStage.close();
                } else {
                    showAlert(dialogStage, "Ошибка", "Не удалось назначить работу", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert(dialogStage, "Ошибка", "Некорректные данные", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 300, 200);
        dialogStage.setTitle("Назначить работу");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
    private void showUpdateExtraWorkDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField workIdField = new TextField();
        DatePicker dateEndPicker = new DatePicker();
        TextField timeHoursField = new TextField();
        Button updateBtn = new Button("Обновить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("ID работы:"), 0, 0);
        grid.add(workIdField, 1, 0);
        grid.add(new Label("Дата окончания:"), 0, 1);
        grid.add(dateEndPicker, 1, 1);
        grid.add(new Label("Время (часы):"), 0, 2);
        grid.add(timeHoursField, 1, 2);
        grid.add(updateBtn, 0, 3);
        grid.add(cancelBtn, 1, 3);

        updateBtn.setOnAction(event -> {
            try {
                int workId = Integer.parseInt(workIdField.getText());
                LocalDate dateEnd = dateEndPicker.getValue();
                double timeHours = Double.parseDouble(timeHoursField.getText());

                if (extraWorksModel.updateExtraWorkDetails(workId, dateEnd, timeHours)) {
                    showAlert(dialogStage, "Получилось", "Ура! Дополнительная работа обновлена", Alert.AlertType.INFORMATION);
                    dialogStage.close();
                } else {
                    showAlert(dialogStage, "Ошибка", "Не удалось обновить работу", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert(dialogStage, "Ошибка", "Некорректные данные", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 350, 200);
        dialogStage.setTitle("Обновить дополнительную работу");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
    private void setupExtraWorksTable() {
        TableColumn<ExtraWork, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ExtraWork, LocalDate> dateStartCol = new TableColumn<>("Дата начала");
        dateStartCol.setCellValueFactory(new PropertyValueFactory<>("dateStart"));

        TableColumn<ExtraWork, String> urgencyCol = new TableColumn<>("Срочность");
        urgencyCol.setCellValueFactory(new PropertyValueFactory<>("urgency"));

        TableColumn<ExtraWork, Integer> workerIdCol = new TableColumn<>("ID отв.");
        workerIdCol.setCellValueFactory(new PropertyValueFactory<>("workerId"));

        TableColumn<ExtraWork, Integer> typeIdCol = new TableColumn<>("Тип работы");
        typeIdCol.setCellValueFactory(new PropertyValueFactory<>("typeId"));

        view.getExtraWorksTable().getColumns().clear();
        view.getExtraWorksTable().getColumns().addAll(idCol, dateStartCol, urgencyCol, workerIdCol, typeIdCol);

        view.getExtraWorksTable().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadUnfinishedExtraWorks();
    }
    private void loadUnfinishedExtraWorks() {
        List<ExtraWork> unfinishedWorks = extraWorksModel.getUnfinishedExtraWorks();

        if (unfinishedWorks.isEmpty()) {
            view.getNoExtraWorksLabel().setVisible(true);
            view.getExtraWorksTable().setVisible(false);
        } else {
            view.getNoExtraWorksLabel().setVisible(false);
            view.getExtraWorksTable().setVisible(true);
            view.getExtraWorksTable().setItems(FXCollections.observableArrayList(unfinishedWorks));
        }
    }
    private void showDeleteExtraWorkDialog() {
        Stage dialogStage = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField workIdField = new TextField();
        Button deleteBtn = new Button("Удалить");
        Button cancelBtn = new Button("Отмена");

        grid.add(new Label("ID работы:"), 0, 0);
        grid.add(workIdField, 1, 0);
        grid.add(deleteBtn, 0, 1);
        grid.add(cancelBtn, 1, 1);

        deleteBtn.setOnAction(event -> {
            try {
                int workId = Integer.parseInt(workIdField.getText());

                if (extraWorksModel.deleteExtraWork(workId)) {
                    showAlert(dialogStage, "Получилось", "Работа удалена", Alert.AlertType.INFORMATION);
                    loadUnfinishedExtraWorks(); // обновляем таблицу
                    dialogStage.close();
                } else {
                    showAlert(dialogStage, "Ошибка", "Не удалось удалить работу", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert(dialogStage, "Ошибка", "Введите корректный ID", Alert.AlertType.ERROR);
            }
        });

        cancelBtn.setOnAction(event -> dialogStage.close());

        Scene dialogScene = new Scene(grid, 300, 150);
        dialogStage.setTitle("Удалить работу");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}