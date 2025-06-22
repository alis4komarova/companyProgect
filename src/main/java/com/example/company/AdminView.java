package com.example.company;

import javafx.stage.Stage; //окно

public class AdminView {
    private Stage primaryStage;
    private int workerId;
    public AdminView(Stage primaryStage, int workerId){
        this.primaryStage = primaryStage;
        this.workerId = workerId;
    }
    public void show() {
        System.out.println("done");
        primaryStage.show();
    }
}