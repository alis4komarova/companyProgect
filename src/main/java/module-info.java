module com.example.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.company to javafx.fxml;
    exports com.example.company;
}