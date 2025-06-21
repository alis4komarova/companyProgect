module com.example.company {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.company to javafx.fxml;
    exports com.example.company;
}