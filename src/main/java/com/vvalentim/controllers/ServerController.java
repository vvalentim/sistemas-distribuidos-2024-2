package com.vvalentim.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerController {
    @FXML
    private TextArea logs;

    @FXML
    public void initialize() {
        this.logs.setEditable(false);
    }

    public String parseMessage(String message) {
        this.logs.appendText("RECEIVED: " + message + "\n");

        message = message.toUpperCase();

        this.logs.appendText("SENT: " + message + "\n");

        return message;
    }
}