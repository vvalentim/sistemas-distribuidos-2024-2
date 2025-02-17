package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.gui.layout.InfoAlert;
import com.vvalentim.models.Notification;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.SaveNotificationDto;
import com.vvalentim.protocol.request.notification.RequestNotificationSave;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryList;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;
import com.vvalentim.protocol.response.notification.ResponseNotificationSaved;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AddNotificationPage extends AbstractPage {
    private MessageParser parser;
    private MessageService fetchCategoriesService;
    private MessageService addNotificationService;

    private ObservableList<NotificationCategory> observableCategories;
    private ChoiceBox<NotificationCategory> choiceCategories;

    private NotificationCategory getSelectedCategory() {
        return this.choiceCategories.getSelectionModel().getSelectedItem();
    }

    @Override
    public void init() {
        this.parser = new MessageParser();

        this.initFetchService();
        this.initAddNotificationService();

        this.runFetchService();

        this.showLoadingOverlay();
    }

    public void initFetchService() {
        this.fetchCategoriesService = new MessageService(ConnectionHandler.getInstance());

        this.fetchCategoriesService.setOnSucceeded(event -> {
            String response = this.fetchCategoriesService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseNotificationCategoryList.class);

            try {
                if (payload instanceof ResponseNotificationCategoryList) {
                    ResponseNotificationCategoryList categoriesPayload = (ResponseNotificationCategoryList) payload;
                    this.observableCategories.setAll(categoriesPayload.categories);

                    if (!this.observableCategories.isEmpty()) {
                        this.choiceCategories.getSelectionModel().selectFirst();
                    }
                } else {
                    System.out.println("Failed to fetch categories.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

                ErrorAlert alert = new ErrorAlert("Falha ao processar a requisição.");
                alert.showAndWait();
            }

            this.hideLoadingOverlay();
        });
    }

    public void runFetchService() {
        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            if (this.fetchCategoriesService.isRunning()) {
                return;
            }

            if (this.fetchCategoriesService.getState() != Worker.State.READY) {
                this.fetchCategoriesService.reset();
            }

            RequestNotificationCategoryList fetchRequest = new RequestNotificationCategoryList(token);
            this.fetchCategoriesService.setClientMessage(this.parser.serialize(fetchRequest));
            this.fetchCategoriesService.start();
            this.showLoadingOverlay();
        }
    }

    public void initAddNotificationService() {
        this.addNotificationService = new MessageService(ConnectionHandler.getInstance());

        this.addNotificationService.setOnSucceeded(event -> {
            String response = this.addNotificationService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseNotificationSaved.class);

            try {
                if (payload instanceof ResponseNotificationSaved) {
                    ResponseNotificationSaved savedPayload = (ResponseNotificationSaved) payload;
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

    public void runAddNotificationService(SaveNotificationDto dto) {
        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            if (this.addNotificationService.isRunning()) {
                return;
            }

            if (this.addNotificationService.getState() != Worker.State.READY) {
                this.addNotificationService.reset();
            }

            RequestNotificationSave fetchRequest = new RequestNotificationSave(token, dto);
            this.addNotificationService.setClientMessage(this.parser.serialize(fetchRequest));
            this.addNotificationService.start();
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
        var labelCategory = new Label("Categoria");
        this.observableCategories = FXCollections.observableArrayList();
        this.choiceCategories = new ChoiceBox<NotificationCategory>(this.observableCategories);
        this.choiceCategories.setMaxWidth(Double.MAX_VALUE);
        var group1 = new VBox(10, labelCategory, choiceCategories);

        var labelTitle = new Label("Titulo");
        var fieldTitle = new TextField();
        fieldTitle.setPrefWidth(100);
        var group2 = new VBox(10, labelTitle, fieldTitle);

        var labelDescription = new Label("Descrição");
        var fieldDescription = new TextArea();
        fieldDescription.setPrefWidth(100);
        var group3 = new VBox(10, labelDescription, fieldDescription);

        var btnAdd = new Button("Adicionar");
        btnAdd.getStyleClass().add(Styles.SUCCESS);
        btnAdd.setOnMouseClicked(event -> {
            String title = fieldTitle.getText();
            String description = fieldDescription.getText();
            NotificationCategory category = this.getSelectedCategory();

            if (!Notification.validateTitle(title) || !Notification.validateDescription(description)) {
                ErrorAlert alert = new ErrorAlert("Campos inválidos.");
                alert.showAndWait();
                return;
            }

            if (category == null) {
                ErrorAlert alert = new ErrorAlert("Selecione uma categoria.");
                alert.showAndWait();
                return;
            }

            SaveNotificationDto dto = new SaveNotificationDto(0, category.getId(), title, description);

            this.runAddNotificationService(dto);
        });

        var btnCancel = new Button("Cancelar");
        btnCancel.getStyleClass().add(Styles.DANGER);
        btnCancel.setOnMouseClicked(event -> this.getStage().close());

        var buttonSpacing = new Region();
        HBox.setHgrow(buttonSpacing, Priority.ALWAYS);

        var groupButtons = new HBox(10, btnCancel, buttonSpacing, btnAdd);

        var groupSpacing = new Region();
        VBox.setVgrow(groupSpacing, Priority.ALWAYS);

        var contents = new VBox(16, group1, group2, group3, groupSpacing, groupButtons);
        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }
}
