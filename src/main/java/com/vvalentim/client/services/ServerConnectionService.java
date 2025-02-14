package com.vvalentim.client.services;

import com.vvalentim.client.ConnectionHandler;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServerConnectionService extends Service<Void> {
    private final ConnectionHandler handler;
    private String serverAddress;
    private int serverPort;

    public ServerConnectionService(ConnectionHandler handler) {
        this.handler = handler;
    }

    public void setServerConfiguration(String address, int port) {
        this.serverAddress = address;
        this.serverPort = port;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                handler.open(serverAddress, serverPort);

                Thread.sleep(500);

                return null;
            }
        };
    }
}
