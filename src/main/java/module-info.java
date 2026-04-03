module com.example.skincareva {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.skincareva to javafx.fxml, com.google.gson;
    exports com.example.skincareva;
}