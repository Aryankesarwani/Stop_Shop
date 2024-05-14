module com.example.stop_shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens com.example.stop_shop to javafx.fxml;
    exports com.example.stop_shop;
}