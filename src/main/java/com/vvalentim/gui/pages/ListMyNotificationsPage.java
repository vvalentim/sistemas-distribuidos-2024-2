package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.gui.layout.InfoAlert;
import com.vvalentim.gui.layout.MainLayer;
import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.notification.RequestNotificationDelete;
import com.vvalentim.protocol.request.subscription.RequestUserNotifications;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;
import com.vvalentim.protocol.response.notification.ResponseNotificationDeleted;
import com.vvalentim.protocol.response.notification.ResponseNotificationList;
import com.vvalentim.protocol.response.subscription.ResponseUserNotifications;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListMyNotificationsPage extends AbstractPage {
    private MessageParser parser;
    private MessageService fetchNotificationsService;

    private ObservableList<Notification> notifications;
    private ListView<Notification> notificationsListView;

    public Notification getSelectedNotification() {
        return this.notificationsListView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void init() {
        this.parser = new MessageParser();

        initFetchService();

        runFetchService();
    }

    private void initFetchService() {
        this.fetchNotificationsService = new MessageService(ConnectionHandler.getInstance());

        this.fetchNotificationsService.setOnSucceeded(event -> {
            String response = this.fetchNotificationsService.getValue();
            ResponsePayload payload = this.parser.deserialize(response, ResponseUserNotifications.class);

            try {
                if (payload instanceof ResponseUserNotifications) {
                    ResponseUserNotifications notificationsPayload = (ResponseUserNotifications) payload;
                    this.notifications.setAll(notificationsPayload.notifications);
                } else {
                    System.out.println("Failed to fetch notifications.");
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
            if (this.fetchNotificationsService.isRunning()) {
                return;
            }

            if (this.fetchNotificationsService.getState() != Worker.State.READY) {
                this.fetchNotificationsService.reset();
            }

            RequestUserNotifications fetchRequest = new RequestUserNotifications(token, token);
            this.fetchNotificationsService.setClientMessage(this.parser.serialize(fetchRequest));
            this.fetchNotificationsService.start();
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
        var title = new Text("Meus avisos");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        this.notifications = FXCollections.observableArrayList();
        this.notificationsListView = new ListView<>(this.notifications);
        this.notificationsListView.setMinHeight(200);
        this.notificationsListView.setCellFactory(category -> new NotificationListItem());
        this.notificationsListView.setPlaceholder(new Label("Nenhum aviso disponível"));
        this.notificationsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        var contents = new VBox(
            10,
            title,
            this.createUpperButtons(),
            this.notificationsListView,
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

        var spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);

        var contents = new HBox(10, spacing, btnRefresh);
        contents.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }

    private HBox createBottomButtons() {
        var btnView = new Button("Visualizar");
        btnView.getStyleClass().add(Styles.ACCENT);
        btnView.setOnMouseClicked(event -> createModalViewNotification(this.getSelectedNotification()));

        var contents = new HBox(10, btnView);
        contents.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }

    private void createModalViewNotification(Notification notification) {
        if (notification == null) {
            ErrorAlert alert = new ErrorAlert("Selecione um aviso.");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        MainLayer root = new MainLayer(Page.VIEW_NOTIFICATION_PAGE);
        Scene scene = new Scene(root.getPane(), 360, 480);
        ViewNotificationPage page = (ViewNotificationPage) root.getBody();

        page.setNotification(notification);

        stage.setScene(scene);
        stage.setTitle("Visualizar aviso");
        stage.setResizable(false);
        stage.initOwner(this.getStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private static class NotificationListItem extends ListCell<Notification> {
        private final HBox root;
        private final Label notificationTitleLabel;

        public NotificationListItem() {
            this.notificationTitleLabel = new Label();
            this.root = new HBox(this.notificationTitleLabel);
            this.root.setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        public void updateItem(Notification notification, boolean empty) {
            super.updateItem(notification, empty);

            if (empty) {
                setGraphic(null);
                return;
            }

            this.notificationTitleLabel.setText(notification.getTitle());
            setGraphic(this.root);
        }
    }
}
