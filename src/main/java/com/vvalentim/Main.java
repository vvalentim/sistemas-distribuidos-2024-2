package com.vvalentim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.vvalentim.controllers.ClientController;
import com.vvalentim.controllers.ServerController;
import com.vvalentim.helpers.View;

public class Main extends Application {
    private static Stage primaryStage;
    private static Stage secondaryStage;

    private static View<ClientController> clientView;
    private static View<ServerController> serverView;

    private static ClientController clientController;
    private static ServerController serverController;

    private static Scene clientScene;
    private static Scene serverScene;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        secondaryStage = new Stage();

        clientView = new View<ClientController>("client", ClientController.class);
        serverView = new View<ServerController>("server", ServerController.class);
        clientController = clientView.getController();
        serverController = serverView.getController();

        clientController.setSubmitHandler((String message) -> {
            return serverController.parseMessage(message);
        });

        clientScene = new Scene(clientView.getNode(), 480, 480);
        serverScene = new Scene(serverView.getNode(), 480, 480);

        primaryStage.setTitle("Client");
        primaryStage.setScene(clientScene);
        primaryStage.show();

        secondaryStage.setTitle("Server");
        secondaryStage.setScene(serverScene);
        secondaryStage.show();

        primaryStage.setX(450);
        primaryStage.setY(240);
        secondaryStage.setX(990);
        secondaryStage.setY(240);
    }

    public static void main(String[] args) {
        launch();
    }

}