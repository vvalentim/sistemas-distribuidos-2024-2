package com.vvalentim.gui.layout;

import javafx.scene.control.Alert;

public class InfoAlert extends Alert {
    public InfoAlert(String message) {
        super(AlertType.INFORMATION);

        this.setTitle("Aviso");
        this.setHeaderText("Aviso");
        this.setContentText(message);
    }
}
