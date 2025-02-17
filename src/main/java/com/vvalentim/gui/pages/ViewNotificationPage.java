package com.vvalentim.gui.pages;

import com.vvalentim.models.Notification;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ViewNotificationPage extends AbstractPage {
    private TextField fieldCategory;
    private TextField fieldTitle;
    private TextArea fieldDescription;

    public void setNotification(Notification notification) {
        this.fieldCategory.setText(notification.getCategory().getName());
        this.fieldTitle.setText(notification.getTitle());
        this.fieldDescription.setText(notification.getDescription());

        this.hideLoadingOverlay();
    }

    @Override
    public void init() {
        this.showLoadingOverlay();
    }

    @Override
    protected void build() {
        var body = this.createContent();
        body.setPadding(new Insets(16));
        body.setFillWidth(true);
        this.getChildren().setAll(body);
    }

    private VBox createContent() {
        var labelCategory = new Label("Categoria");
        this.fieldCategory = new TextField();
        this.fieldCategory.setPrefWidth(100);
        this.fieldCategory.setEditable(false);
        var group1 = new VBox(10, labelCategory, fieldCategory);

        var labelTitle = new Label("Titulo");
        this.fieldTitle = new TextField();
        this.fieldTitle.setPrefWidth(100);
        this.fieldTitle.setEditable(false);
        var group2 = new VBox(10, labelTitle, fieldTitle);

        var labelDescription = new Label("Descrição");
        this.fieldDescription = new TextArea();
        this.fieldDescription.setPrefWidth(100);
        this.fieldDescription.setEditable(false);
        var group3 = new VBox(10, labelDescription, fieldDescription);

        var contents = new VBox(16, group1, group2, group3);
        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }
}
