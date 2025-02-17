package com.vvalentim.gui;

import atlantafx.base.theme.PrimerLight;
import com.vvalentim.gui.layout.MainLayer;
import com.vvalentim.gui.pages.Page;
import com.vvalentim.server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerLauncher extends Application {
    public static Server server;

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        MainLayer root = new MainLayer(Page.SERVER_CONFIGURATION_PAGE);

        Scene scene = new Scene(root.getPane(), 480, 480);
        stage.setScene(scene);
        stage.setTitle("Servidor Sistemas Distribuídos");

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> closeCallback());


        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }

    public void closeCallback() {
        if (server.isAlive()) {
            server.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
