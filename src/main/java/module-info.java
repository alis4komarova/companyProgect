module com.example.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.company to javafx.fxml;
    exports com.example.company;
}