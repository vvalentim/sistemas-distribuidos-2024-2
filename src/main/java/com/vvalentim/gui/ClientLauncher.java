package com.vvalentim.gui;

import atlantafx.base.theme.PrimerLight;
import com.vvalentim.gui.pages.ClientConnectionPage;
import com.vvalentim.gui.pages.HomePage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientLauncher extends Application {
    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        StackPane mainLayer = new StackPane(new ClientConnectionPage());

        Scene scene = new Scene(mainLayer, 540, 420);
        stage.setScene(scene);
        stage.setOnCloseRequest(t -> Platform.exit());

        stage.setResizable(false);
        stage.setWidth(800);
        stage.setHeight(640);

        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
