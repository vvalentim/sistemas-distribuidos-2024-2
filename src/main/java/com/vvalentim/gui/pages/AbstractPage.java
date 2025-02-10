package com.vvalentim.gui.pages;

import javafx.scene.layout.StackPane;

import java.lang.reflect.Constructor;

abstract public class AbstractPage extends StackPane {
    public AbstractPage() {
        this.build();
        this.init();
    }
    abstract protected void build();
    abstract protected void init();

    protected void changePage(Page page) {
        StackPane mainPane = (StackPane) this.getParent();

        try {
            Constructor<?> constructor = page.pageClass.getConstructor();

            StackPane pane = (StackPane) constructor.newInstance();

            mainPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
