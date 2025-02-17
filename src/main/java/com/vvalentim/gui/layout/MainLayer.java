package com.vvalentim.gui.layout;

import com.vvalentim.gui.pages.AbstractPage;
import com.vvalentim.gui.pages.Page;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;

public class MainLayer {
    private static final Page DEFAULT_PAGE = Page.SERVER_CONNECTION_PAGE;

    private static final String OVERLAY_LOADING_ID = "overlay-loading";

    private final StackPane root;

    private Page currentPage;

    public MainLayer() {
        this(DEFAULT_PAGE);
    }

    public MainLayer(Page initialPage) {
        this.root = new StackPane();

        this.loadPage(initialPage);
    }

    public StackPane getPane() {
        return this.root;
    }

    public void loadPage(Page page) {
        if (page == currentPage) {
            return;
        }

        try {
            Constructor<?> constructor = page.pageClass.getDeclaredConstructor();

            AbstractPage pane = (AbstractPage) constructor.newInstance();

            pane.setRoot(this);
            this.root.getChildren().setAll(pane);
            this.currentPage = page;

            pane.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showLoadingOverlay() {
        if (this.root.getChildren().stream().noneMatch(node -> node.getId() != null && node.getId().equals(OVERLAY_LOADING_ID))) {
            ProgressIndicator indicator = new ProgressIndicator(0);
            indicator.setMinSize(100, 100);
            indicator.setProgress(-1d);

            StackPane overlay = new StackPane(indicator);
            overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
            overlay.setId(OVERLAY_LOADING_ID);

            this.root.getChildren().add(overlay);
        }
    }

    public void hideLoadingOverlay() {
        this.root.getChildren().removeIf(node -> node.getId() != null && node.getId().equals(OVERLAY_LOADING_ID));
    }
}
