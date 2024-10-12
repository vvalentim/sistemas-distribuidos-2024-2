module com.vvalentim {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.vvalentim to javafx.fxml;
    opens com.vvalentim.controllers to javafx.fxml;

    exports com.vvalentim;
    exports com.vvalentim.controllers;
}
