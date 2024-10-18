package com.vvalentim.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.vvalentim.client.controllers.ClientController;

public class ClientGUI extends Application {
    private static ConnectionHandler connectionHandler;

    private static Stage stage;

    private static View<ClientController> view;

    private static Scene scene;

    public static ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    @Override
    public void start(Stage stage) throws IOException {
        ClientGUI.connectionHandler = new ConnectionHandler();

        ClientGUI.stage = stage;
        ClientGUI.view = new View<ClientController>("client", ClientController.class);
        ClientGUI.scene = new Scene(ClientGUI.view.getNode(), 480, 480);

        ClientGUI.stage.setTitle("Client");
        ClientGUI.stage.setScene(ClientGUI.scene);

        ClientGUI.stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}