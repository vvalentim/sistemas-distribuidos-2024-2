package com.vvalentim.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.function.Function;

public class ClientController {
    private Function<String, String> handlerSubmit;

    @FXML
    private TextArea messageInput;

    @FXML
    private TextArea messageOutput;

    @FXML
    public void initialize() {
        this.messageOutput.setEditable(false);
    }

    @FXML
    private void submitMessage(ActionEvent event) {
        String message = this.messageInput.getText();

        this.messageInput.setText("");

        if (this.handlerSubmit != null) {
            this.messageOutput.appendText("RESPONSE: " + this.handlerSubmit.apply(message) + "\n");
        }
    }

    public void setSubmitHandler(Function<String, String> handler) {
        this.handlerSubmit = handler;
    }
}
