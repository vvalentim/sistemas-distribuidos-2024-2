package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.gui.layout.InfoAlert;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategorySave;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategorySaved;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class EditCategoryPage extends AbstractPage{
    private NotificationCategory category;
    private TextField fieldName;

    private MessageParser parser;
    private MessageService editCategoryService;

    public void setCategory(NotificationCategory category) {
        this.category = category;

        this.fieldName.setText(category.getName());
    }

    @Override
    public void init() {
        this.parser = new MessageParser();

        this.editCategoryService = new MessageService(ConnectionHandler.getInstance());

        this.editCategoryService.setOnSucceeded(event -> {
            String response = this.editCategoryService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseNotificationCategorySaved.class);

            try {
                if (payload instanceof ResponseNotificationCategorySaved) {
                    ResponseNotificationCategorySaved savedPayload = (ResponseNotificationCategorySaved) payload;
                    InfoAlert alert = new InfoAlert(savedPayload.message);
                    alert.showAndWait();
                    this.getStage().close();
                    return;
                } else {
                    ResponseGenericError errorPayload = (ResponseGenericError) payload;
                    ErrorAlert alert = new ErrorAlert(errorPayload.message);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

                ErrorAlert alert = new ErrorAlert("Falha ao processar a requisição.");
                alert.showAndWait();
            }

            this.hideLoadingOverlay();
        });
    }

    public void runAddCategoryService(String name) {
        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            if (this.editCategoryService.isRunning()) {
                return;
            }

            if (this.editCategoryService.getState() != Worker.State.READY) {
                this.editCategoryService.reset();
            }

            NotificationCategory category = new NotificationCategory(this.category.getId(), name);
            RequestNotificationCategorySave saveRequest = new RequestNotificationCategorySave(token, category);
            this.editCategoryService.setClientMessage(this.parser.serialize(saveRequest));
            this.editCategoryService.start();
            this.showLoadingOverlay();
        }
    }

    @Override
    protected void build() {
        var body = this.createContent();
        body.setPadding(new Insets(16));
        body.setFillWidth(true);
        this.getChildren().setAll(body);
    }

    private VBox createContent() {
        var labelName = new Label("Nome");
        this.fieldName = new TextField();
        this.fieldName.setPrefWidth(100);

        var groupInput = new VBox(10, labelName, this.fieldName);

        var btnEdit = new Button("Salvar");
        btnEdit.getStyleClass().add(Styles.SUCCESS);
        btnEdit.setOnMouseClicked(event -> {
            String name = this.fieldName.getText();

            if (!NotificationCategory.validateName(name)) {
                ErrorAlert alert = new ErrorAlert("O nome da categoria é inválido.");
                alert.showAndWait();
                return;
            }

            this.runAddCategoryService(name);
        });

        var btnCancel = new Button("Cancelar");
        btnCancel.getStyleClass().add(Styles.DANGER);
        btnCancel.setOnMouseClicked(event -> this.getStage().close());

        var buttonSpacing = new Region();
        HBox.setHgrow(buttonSpacing, Priority.ALWAYS);

        var groupButtons = new HBox(10, btnCancel, buttonSpacing, btnEdit);

        var groupSpacing = new Region();
        VBox.setVgrow(groupSpacing, Priority.ALWAYS);

        var contents = new VBox(16, groupInput, groupSpacing, groupButtons);
        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }
}
