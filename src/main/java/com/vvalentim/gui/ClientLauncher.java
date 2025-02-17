package com.vvalentim.gui;

import atlantafx.base.theme.PrimerLight;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.MainLayer;
import com.vvalentim.protocol.request.authentication.RequestLogout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientLauncher extends Application {
    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        MainLayer root = new MainLayer();

        Scene scene = new Scene(root.getPane(), 800, 640);
        stage.setScene(scene);
        stage.setTitle("Cliente Sistemas Distribuídos");

        stage.setResizable(false);

        stage.setOnCloseRequest(event -> closeCallback());

        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }

    public void closeCallback() {
        ConnectionHandler handler = ConnectionHandler.getInstance();

        if (handler.isOpen()) {
            if (handler.getToken() != null) {
                MessageService logoutService = getLogoutService(handler);
                logoutService.start();
                return;
            }

            ConnectionHandler.getInstance().close();
        }

        Platform.exit();
        System.exit(0);
    }

    private static MessageService getLogoutService(ConnectionHandler handler) {
        MessageParser parser = new MessageParser();
        MessageService logoutService = new MessageService(handler);

        logoutService.setOnSucceeded(event -> {
            System.out.println("LOGOUT OK");
            ConnectionHandler.getInstance().close();
            Platform.exit();
            System.exit(0);
        });

        logoutService.setOnFailed(event -> {
            System.out.println("LOGOUT FAILED");
            Platform.exit();
            System.exit(1);
        });

        RequestLogout logoutPayload = new RequestLogout(handler.getToken());
        logoutService.setClientMessage(parser.serialize(logoutPayload));
        return logoutService;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
