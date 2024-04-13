module com.picksome.picksome {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires java.net.http;
    requires com.google.gson;


    opens com.picksome.picksome to javafx.fxml, com.google.gson;
    exports com.picksome.picksome;
    exports com.picksome.picksome.services;
    opens com.picksome.picksome.services to com.google.gson, javafx.fxml;
    exports com.picksome.picksome.managers;
    opens com.picksome.picksome.managers to com.google.gson, javafx.fxml;
}