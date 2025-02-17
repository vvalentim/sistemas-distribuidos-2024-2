package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.ConfirmationDialog;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.gui.layout.InfoAlert;
import com.vvalentim.gui.layout.MainLayer;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryDelete;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryList;
import com.vvalentim.protocol.request.subscription.RequestSubscribeToCategory;
import com.vvalentim.protocol.request.subscription.RequestUnsubscribeFromCategory;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryDeleted;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryList;
import com.vvalentim.protocol.response.subscription.ResponseSubscribedOnCategory;
import com.vvalentim.protocol.response.subscription.ResponseUnsubscribeFromCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListCategoriesPage extends AbstractPage {
    private MessageParser parser;
    private MessageService fetchCategoriesService;
    private MessageService subscribeService;
    private MessageService unsubscribeService;
    private MessageService deleteCategoryService;

    private ObservableList<NotificationCategory> categories;
    private ListView<NotificationCategory> categoriesListView;

    /* TODO: update notification category */

    public NotificationCategory getSelectedCategory() {
        return this.categoriesListView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void init() {
        this.parser = new MessageParser();

        initFetchService();
        initSubscribeService();
        initUnsubscribeService();
        initDeleteCategoryService();

        runFetchService();
    }

    private void initFetchService() {
        this.fetchCategoriesService = new MessageService(ConnectionHandler.getInstance());

        this.fetchCategoriesService.setOnSucceeded(event -> {
            String response = this.fetchCategoriesService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseNotificationCategoryList.class);

            try {
                if (payload instanceof ResponseNotificationCategoryList) {
                    ResponseNotificationCategoryList categoriesPayload = (ResponseNotificationCategoryList) payload;
                    this.categories.setAll(categoriesPayload.categories);
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

    private void runFetchService() {
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

    private void initSubscribeService() {
        this.subscribeService = new MessageService(ConnectionHandler.getInstance());

        this.subscribeService.setOnSucceeded(event -> {
            String response = this.subscribeService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseSubscribedOnCategory.class);

            try {
                if (payload instanceof ResponseSubscribedOnCategory) {
                    ResponseSubscribedOnCategory subscribedPayload = (ResponseSubscribedOnCategory) payload;
                    InfoAlert alert = new InfoAlert(subscribedPayload.message);
                    alert.showAndWait();
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

    private void runSubscribeService(int categoryId) {
        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            if (this.subscribeService.isRunning()) {
                return;
            }

            if (this.subscribeService.getState() != Worker.State.READY) {
                this.subscribeService.reset();
            }

            RequestSubscribeToCategory subscribeRequest = new RequestSubscribeToCategory(token, token, categoryId);
            this.subscribeService.setClientMessage(this.parser.serialize(subscribeRequest));
            this.subscribeService.start();
            this.showLoadingOverlay();
        }
    }

    private void initUnsubscribeService() {
        this.unsubscribeService = new MessageService(ConnectionHandler.getInstance());

        this.unsubscribeService.setOnSucceeded(event -> {
            String response = this.unsubscribeService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseUnsubscribeFromCategory.class);

            try {
                if (payload instanceof ResponseUnsubscribeFromCategory) {
                    ResponseUnsubscribeFromCategory unsubscribedPayload = (ResponseUnsubscribeFromCategory) payload;
                    InfoAlert alert = new InfoAlert(unsubscribedPayload.message);
                    alert.showAndWait();
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

    private void runUnsubscribeService(int categoryId) {
        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            if (this.unsubscribeService.isRunning()) {
                return;
            }

            if (this.unsubscribeService.getState() != Worker.State.READY) {
                this.unsubscribeService.reset();
            }

            RequestUnsubscribeFromCategory unsubscribeRequest = new RequestUnsubscribeFromCategory(token, token, categoryId);
            this.unsubscribeService.setClientMessage(this.parser.serialize(unsubscribeRequest));
            this.unsubscribeService.start();
            this.showLoadingOverlay();
        }
    }

    private void initDeleteCategoryService() {
        this.deleteCategoryService = new MessageService(ConnectionHandler.getInstance());

        this.deleteCategoryService.setOnSucceeded(event -> {
            String response = this.deleteCategoryService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseNotificationCategoryDeleted.class);

            try {
                if (payload instanceof ResponseNotificationCategoryDeleted) {
                    ResponseNotificationCategoryDeleted deletedPayload = (ResponseNotificationCategoryDeleted) payload;
                    InfoAlert alert = new InfoAlert(deletedPayload.message);
                    alert.showAndWait();
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

    private void runDeleteCategoryService(int categoryId) {
        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            if (this.deleteCategoryService.isRunning()) {
                return;
            }

            if (this.deleteCategoryService.getState() != Worker.State.READY) {
                this.deleteCategoryService.reset();
            }

            RequestNotificationCategoryDelete deleteCategoryRequest = new RequestNotificationCategoryDelete(token, categoryId);
            this.deleteCategoryService.setClientMessage(this.parser.serialize(deleteCategoryRequest));
            this.deleteCategoryService.start();
            this.showLoadingOverlay();
        }
    }

    @Override
    protected void build() {
        var body = this.createBodyWrapper();
        body.setPadding(Insets.EMPTY);
        body.setFillHeight(true);
        this.getChildren().setAll(body);
    }

    private HBox createBodyWrapper() {
        this.addSidebarNavButton("Meu cadastro", Page.MANAGE_USER_PAGE);
        this.addSidebarNavButton("Meus avisos", Page.LIST_MY_NOTIFICATIONS_PAGE);
        this.addSidebarNavButton("Avisos", Page.LIST_NOTIFICATIONS_PAGE);
        this.addSidebarNavButton("Categorias", Page.LIST_CATEGORIES_PAGE);

        return new HBox(15, createSidebar(), createContent());
    }

    private VBox createContent() {
        var title = new Text("Lista de categorias");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        this.categories = FXCollections.observableArrayList();
        this.categoriesListView = new ListView<>(this.categories);
        this.categoriesListView.setMinHeight(200);
        this.categoriesListView.setCellFactory(category -> new CategoryListItem());
        this.categoriesListView.setPlaceholder(new Label("Nenhuma categoria cadastrada"));
        this.categoriesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        var contents = new VBox(
            10,
            title,
            this.createUpperButtons(),
            this.categoriesListView,
            this.createBottomButtons()
        );

        contents.setPadding(new Insets(20, 20, 20, 20));
        contents.setFillWidth(true);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }

    private HBox createUpperButtons() {
        var btnRefresh = new Button("Atualizar lista");
        btnRefresh.getStyleClass().add(Styles.ACCENT);
        btnRefresh.setOnMouseClicked(event -> this.runFetchService());

        var btnDelete = new Button("Excluir");
        btnDelete.getStyleClass().add(Styles.DANGER);
        btnDelete.setOnMouseClicked(event -> {
            NotificationCategory category = this.getSelectedCategory();

            if (category != null) {
                ConfirmationDialog confirm = new ConfirmationDialog("Tem certeza que deseja excluir \"" + category.getName() + "\"?");
                confirm.showAndWait();

                if (confirm.getResult() == ButtonType.OK) {
                    this.runDeleteCategoryService(category.getId());
                }
            } else {
                ErrorAlert alert = new ErrorAlert("Selecione uma categoria.");
                alert.showAndWait();
            }
        });

        var btnAdd = new Button("Adicionar");
        btnAdd.getStyleClass().add(Styles.SUCCESS);
        btnAdd.setOnMouseClicked(event -> createModalAddCategory());

        var spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);

        var contents = new HBox(10, btnAdd, btnDelete, spacing, btnRefresh);
        contents.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }

    private HBox createBottomButtons() {
        var btnSubscribe = new Button("Cadastrar-se");
        btnSubscribe.getStyleClass().add(Styles.ACCENT);
        btnSubscribe.setOnMouseClicked(event -> {
            NotificationCategory category = this.getSelectedCategory();

            if (category != null) {
                this.runSubscribeService(category.getId());
            } else {
                ErrorAlert alert = new ErrorAlert("Selecione uma categoria.");
                alert.showAndWait();
            }
        });

        var btnUnsubscribe = new Button("Descadastrar-se");
        btnUnsubscribe.getStyleClass().add(Styles.DANGER);
        btnUnsubscribe.setOnMouseClicked(event -> {
            NotificationCategory category = this.getSelectedCategory();

            if (category != null) {
                this.runUnsubscribeService(category.getId());
            } else {
                ErrorAlert alert = new ErrorAlert("Selecione uma categoria.");
                alert.showAndWait();
            }
        });

        var spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);

        var contents = new HBox(10, btnSubscribe, spacing, btnUnsubscribe);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }

    private void createModalAddCategory() {
        Stage stage = new Stage();
        MainLayer root = new MainLayer(Page.ADD_CATEGORY_PAGE);
        Scene scene = new Scene(root.getPane(), 360, 180);

        stage.setScene(scene);
        stage.setTitle("Adicionar categoria");
        stage.setResizable(false);
        stage.initOwner(this.getStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static class CategoryListItem extends ListCell<NotificationCategory> {
        private final HBox root;
        private final Label categoryNameLabel;

        public CategoryListItem() {
            this.categoryNameLabel = new Label();
            this.root = new HBox(this.categoryNameLabel);
            this.root.setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        public void updateItem(NotificationCategory category, boolean empty) {
            super.updateItem(category, empty);

            if (empty) {
                setGraphic(null);
                return;
            }

            this.categoryNameLabel.setText(category.getName());
            setGraphic(this.root);
        }
    }
}
