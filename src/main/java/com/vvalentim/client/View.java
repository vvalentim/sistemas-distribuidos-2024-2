package com.vvalentim.client;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class View<T> {
    private static final String dir = "views/";

    private final Parent node;
    private final Object controller;
    private final Class<T> type;

    public View(String filename, Class<T> type) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClientGUI.class.getResource(dir + filename + ".fxml"));

        this.node = loader.load();
        this.controller = loader.getController();
        this.type = type;
    }

    public Parent getNode() {
        return this.node;
    }

    public T getController() {
        return this.type.cast(this.controller);
    }
}
