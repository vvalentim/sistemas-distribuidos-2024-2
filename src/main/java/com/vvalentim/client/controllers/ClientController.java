package com.vvalentim.client.controllers;

import com.vvalentim.client.ClientGUI;
import com.vvalentim.client.services.MessageService;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ClientController {
    private MessageService messageService;

    @FXML
    private TextArea messageInput;

    @FXML
    private TextArea messageOutput;

    @FXML
    public void initialize() throws IOException {
        this.messageOutput.setEditable(false);
        this.messageService = new MessageService(ClientGUI.getConnectionHandler());

        this.messageService.setOnSucceeded(event -> {
            String response = this.messageService.getValue();

            if (response.equals("BYE")) {
                ClientGUI.getConnectionHandler().close();
            }

            if (!response.isBlank()) {
                this.messageOutput.appendText(response + "\n");
            }
        });

        this.messageService.setOnFailed(event -> {
            System.out.println("Failed to communicate with the server (" + this.messageService.getException().getMessage() + ").");
        });
    }

    @FXML
    private void submitMessage(ActionEvent event) {
        if (!this.messageInput.getText().isBlank() && !this.messageService.isRunning()) {
            if (this.messageService.getState() != Worker.State.READY) {
                this.messageService.reset();
            }

            this.messageService.setClientMessage(this.messageInput.getText());
            this.messageService.start();
            this.messageInput.setText("");
        }
    }
}
