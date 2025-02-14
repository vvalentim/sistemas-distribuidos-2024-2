package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryList;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ListCategories extends AbstractPage {
    private MessageService fetchCategoriesService;
    private MessageParser parser;

    private ObservableList<NotificationCategory> categories;
    private ListView<NotificationCategory> categoriesListView;

    @Override
    public void init() {
        String token = ConnectionHandler.getInstance().getToken();

        this.parser = new MessageParser();
        this.fetchCategoriesService = new MessageService(ConnectionHandler.getInstance());

        this.fetchCategoriesService.setOnSucceeded(event -> {
            String response = this.fetchCategoriesService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseNotificationCategoryList.class);

            if (payload instanceof ResponseNotificationCategoryList) {
                ResponseNotificationCategoryList categoriesPayload = (ResponseNotificationCategoryList) payload;
                this.categories.setAll(categoriesPayload.categories);
            } else {
                System.out.println("Failed to fetch categories.");
            }

            this.hideLoadingOverlay();
        });

        if (token != null) {
            RequestNotificationCategoryList fetchRequest = new RequestNotificationCategoryList(token);
            this.fetchCategoriesService.setClientMessage(parser.serialize(fetchRequest));
            this.fetchCategoriesService.start();
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
        this.addSidebarNavButton("Listar categorias", Page.LIST_CATEGORIES_PAGE);

        return new HBox(15, createSidebar(), createContent());
    }

    private VBox createContent() {
        var title = new Text("Lista de categorias");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        this.categories = FXCollections.observableArrayList();
        this.categoriesListView = new ListView<>(this.categories);
        categoriesListView.setMinHeight(200);
        categoriesListView.setCellFactory(category -> new CategoryListItem());

        var contents = new VBox(30, title, categoriesListView);
        contents.setPadding(new Insets(20, 20, 20, 20));
        contents.setFillWidth(true);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
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

            categoryNameLabel.setText(category.getName());
            setGraphic(root);
        }
    }
}
