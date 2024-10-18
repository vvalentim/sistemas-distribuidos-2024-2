module com.vvalentim {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    exports com.vvalentim.client;
    opens com.vvalentim.client to javafx.fxml;

    exports com.vvalentim.client.controllers;
    opens com.vvalentim.client.controllers to javafx.fxml;

    exports com.vvalentim.server;
}
