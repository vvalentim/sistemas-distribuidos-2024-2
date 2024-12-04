package com.vvalentim.client;

import com.vvalentim.client.controllers.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUI extends Application {
    private static ConnectionHandler connectionHandler;

    private static Stage stage;

    private static View<LoginController> view;

    private static Scene scene;

    public static ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        ClientGUI.connectionHandler = new ConnectionHandler("192.168.1.103", 5200);

        ClientGUI.stage = stage;
        ClientGUI.view = new View<LoginController>("login", LoginController.class);
        ClientGUI.scene = new Scene(ClientGUI.view.getNode(), 300, 300);

        ClientGUI.stage.setTitle("Client");
        ClientGUI.stage.setScene(ClientGUI.scene);

        ClientGUI.stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}