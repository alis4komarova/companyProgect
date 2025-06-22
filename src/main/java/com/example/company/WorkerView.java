package com.example.company;

import javafx.stage.Stage;

public class WorkerView {
    private Stage primaryStage;
    private int workerId;

    public WorkerView(Stage primaryStage, int workerId) {
        this.primaryStage = primaryStage;
        this.workerId = workerId;
    }

    public void show() {
        System.out.println("done");
        primaryStage.show();
    }
}
