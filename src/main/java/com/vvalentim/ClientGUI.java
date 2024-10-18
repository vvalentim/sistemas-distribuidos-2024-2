package com.vvalentim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.vvalentim.controllers.ClientController;
import com.vvalentim.helpers.View;

public class ClientGUI extends Application {
    private static Stage stage;

    private static View<ClientController> view;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
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