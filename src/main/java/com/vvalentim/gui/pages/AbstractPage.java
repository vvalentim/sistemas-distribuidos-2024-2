package com.vvalentim.gui.pages;

import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.MainLayer;
import com.vvalentim.protocol.request.authentication.RequestLogin;
import com.vvalentim.protocol.request.authentication.RequestLogout;
import com.vvalentim.protocol.response.authentication.ResponseLogin;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedHashMap;

abstract public class AbstractPage extends StackPane {
    private MainLayer root;

    private final HashMap<String, Page> sidebarNavButtons;

    private final MessageService logoutService;

    public AbstractPage() {
        this.sidebarNavButtons = new LinkedHashMap<>();
        this.logoutService = new MessageService(ConnectionHandler.getInstance());

        this.logoutService.setOnSucceeded(event -> this.changePage(Page.LOGIN_PAGE));

        this.build();
    }

    final public void addSidebarNavButton(String label, Page page) {
        this.sidebarNavButtons.put(label, page);
    }

    final public void setRoot(MainLayer root) {
        this.root = root;
    }

    final protected void changePage(Page page) {
        if (this.root != null) {
            this.root.loadPage(page);
        }
    }

    final protected void showLoadingOverlay() {
        if (this.root != null) {
            this.root.showLoadingOverlay();
        }
    }

    final protected void hideLoadingOverlay() {
        if (this.root != null) {
            this.root.hideLoadingOverlay();
        }
    }

    protected VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        sidebar.setPadding(new Insets(25, 10, 25, 10));
        sidebar.setFillWidth(true);
        sidebar.setPrefWidth(200);

        this.sidebarNavButtons.forEach((label, page) -> {
            Button navButton = new Button(label);

            navButton.setMaxWidth(Double.MAX_VALUE);
            navButton.setOnMouseClicked(event -> this.changePage(page));

            sidebar.getChildren().add(navButton);
        });

        Region spacing = new Region();
        VBox.setVgrow(spacing, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setOnMouseClicked(event -> logoutCallback());

        sidebar.getChildren().addAll(spacing, logoutBtn);

        return sidebar;
    }

    protected void logoutCallback() {
        if (this.logoutService.isRunning()) {
            return;
        }

        if (this.logoutService.getState() != Worker.State.READY) {
            this.logoutService.reset();
        }

        try {
            MessageParser parser = new MessageParser();
            RequestLogout payload = new RequestLogout(ConnectionHandler.getInstance().getToken());

            this.logoutService.setClientMessage(parser.serialize(payload));
            this.logoutService.start();
            this.showLoadingOverlay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract protected void build();
    abstract public void init();
}
