package com.vvalentim.client.services;

import com.vvalentim.client.ConnectionHandler;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MessageService extends Service<String> {
    private final ConnectionHandler handler;
    private String clientMessage;

    public MessageService(ConnectionHandler handler) {
        this.handler = handler;
    }

    public void setClientMessage(String message) {
        this.clientMessage = message;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                handler.sendMessage(clientMessage);

                return handler.getResponse();
            }
        };
    }
}
