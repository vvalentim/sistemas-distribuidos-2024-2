package com.vvalentim.gui;

import atlantafx.base.theme.PrimerLight;
import com.vvalentim.gui.layout.MainLayer;
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
        stage.setTitle("EP3 - Sistemas Distribuídos");
        stage.setOnCloseRequest(t -> Platform.exit());

        stage.setResizable(false);

        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
