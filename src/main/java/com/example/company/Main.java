package com.example.company;
import javafx.application.Application; //для fx
import javafx.stage.Stage; //окно

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Users model = new Users();
        AuthView view = new AuthView(primaryStage, model);
        AuthController controller = new AuthController(primaryStage, model, view);

        model.addObserver(controller); //контроллер как наблюдатель модели

        controller.show();//запуск интерфейса
    }
    public static void main(String[] args) {
        launch(args);
    }
}