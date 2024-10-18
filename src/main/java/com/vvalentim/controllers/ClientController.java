package com.vvalentim.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ClientController {
    private final String hostname = "127.0.0.1";
    private final int port = 3000;

    @FXML
    private TextArea messageInput;

    @FXML
    private TextArea messageOutput;

    @FXML
    public void initialize() throws IOException {
        this.messageOutput.setEditable(false);
    }

    @FXML
    private void submitMessage(ActionEvent event) {
        String message = this.messageInput.getText();

        this.messageInput.setText("");
    }
}
