package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.ConfirmationDialog;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.gui.layout.InfoAlert;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.users.RequestUserDelete;
import com.vvalentim.protocol.request.users.RequestUserFind;
import com.vvalentim.protocol.request.users.RequestUserUpdate;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;
import com.vvalentim.protocol.response.users.ResponseUserDeleted;
import com.vvalentim.protocol.response.users.ResponseUserFound;
import com.vvalentim.protocol.response.users.ResponseUserUpdated;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ManageUserPage extends AbstractPage {
    private MessageService fetchUserService;
    private MessageService updateUserService;
    private MessageService deleteUserService;
    private MessageParser parser;

    private TextField nameField;
    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    public void init() {
        this.parser = new MessageParser();
        this.fetchUserService = new MessageService(ConnectionHandler.getInstance());
        this.updateUserService = new MessageService(ConnectionHandler.getInstance());
        this.deleteUserService = new MessageService(ConnectionHandler.getInstance());

        String token = ConnectionHandler.getInstance().getToken();

        if (token != null) {
            this.initFetchService();
            this.initUpdateService();
            this.initDeleteService();
        }
    }

    private void initFetchService() {
        String token = ConnectionHandler.getInstance().getToken();

        RequestUserFind payloadInitialFetch = new RequestUserFind(token, token);

        this.fetchUserService.setClientMessage(parser.serialize(payloadInitialFetch));

        this.fetchUserService.setOnSucceeded(event -> {
            String response = this.fetchUserService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseUserFound.class);

            if (payload instanceof ResponseUserFound) {
                User user = ((ResponseUserFound) payload).user;

                if (user != null) {
                    this.nameField.setText(user.getName());
                    this.usernameField.setText(user.getUsername());
                }
            }

            this.hideLoadingOverlay();
        });

        this.fetchUserService.start();
        this.showLoadingOverlay();
    }

    private void initUpdateService() {
        this.updateUserService.setOnSucceeded(event -> {
            String response = this.updateUserService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseUserUpdated.class);

            if (payload instanceof ResponseUserUpdated) {
                ResponseUserUpdated updatedPayload = (ResponseUserUpdated) payload;
                InfoAlert alert = new InfoAlert(updatedPayload.message);
                alert.showAndWait();
            } else {
                ResponseGenericError errorPayload = (ResponseGenericError) payload;
                ErrorAlert alert = new ErrorAlert(errorPayload.message);
                alert.showAndWait();
            }

            this.hideLoadingOverlay();
        });
    }

    private void initDeleteService() {
        this.deleteUserService.setOnSucceeded(event -> {
            String response = this.deleteUserService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseUserDeleted.class);

            if (payload instanceof ResponseGenericError) {
                ResponseGenericError errorPayload = (ResponseGenericError) payload;
                ErrorAlert alert = new ErrorAlert(errorPayload.message);
                alert.showAndWait();
                this.hideLoadingOverlay();

                return;
            }

            ResponseUserDeleted deletedPayload = (ResponseUserDeleted) payload;
            InfoAlert alert = new InfoAlert(deletedPayload.message);
            alert.showAndWait();
            this.changePage(Page.LOGIN_PAGE);
        });
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
        var title = new Text("Meu cadastro");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        var labelUsername = new Label("RA do usuário");
        usernameField = new TextField();
        usernameField.setPrefWidth(200);
        usernameField.setEditable(false);
        var group1 = new VBox(labelUsername, usernameField);

        var labelName = new Label("Nome");
        nameField = new TextField();
        nameField.setPrefWidth(200);
        var group2 = new VBox(labelName, nameField);

        var labelPasswordField = new Label("Senha");
        passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        var group3 = new VBox(labelPasswordField, passwordField);

        Region verticalSpacing = new Region();
        VBox.setVgrow(verticalSpacing, Priority.ALWAYS);

        Region buttonSpacing = new Region();
        HBox.setHgrow(buttonSpacing, Priority.ALWAYS);

        var wrapperButtons = new HBox(
            this.createDeleteUserButton(),
            buttonSpacing,
            this.createUpdateUserButton()
        );

        var contents = new VBox(30, title, group1, group2, group3, verticalSpacing, wrapperButtons);
        contents.setPadding(new Insets(20, 20, 20, 20));
        contents.setFillWidth(true);

        HBox.setHgrow(contents, Priority.ALWAYS);

        return contents;
    }

    private Button createUpdateUserButton() {
        var button = new Button("Atualizar cadastro");
        button.setDefaultButton(true);

        button.setOnMouseClicked(event -> {
            if (this.updateUserService.isRunning()) {
                return;
            }

            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!User.validateName(name) || !User.validatePassword(password)) {
                ErrorAlert alert = new ErrorAlert("Nome ou senha inválido(s), preencha os campos corretamente.");
                alert.showAndWait();
                return;
            }

            if (this.updateUserService.getState() != Worker.State.READY) {
                this.updateUserService.reset();
            }

            User user = new User(name, username, password);
            String token = ConnectionHandler.getInstance().getToken();
            RequestUserUpdate updateRequest = new RequestUserUpdate(token, user);
            String payload = this.parser.serialize(updateRequest);

            this.updateUserService.setClientMessage(payload);
            this.updateUserService.start();
            this.showLoadingOverlay();
        });

        return button;
    }

    private Button createDeleteUserButton() {
        var button = new Button("Excluir cadastro");
        button.getStyleClass().add(Styles.DANGER);

        button.setOnMouseClicked(event -> {
            if (this.deleteUserService.isRunning()) {
                return;
            }

            if (this.deleteUserService.getState() != Worker.State.READY) {
                this.deleteUserService.reset();
            }

            ConfirmationDialog confirm = new ConfirmationDialog("Tem certeza que deseja excluir seu cadastro?");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.OK) {
                String token = ConnectionHandler.getInstance().getToken();
                String username = this.usernameField.getText();
                RequestUserDelete deleteRequest = new RequestUserDelete(token, username);
                String payload = this.parser.serialize(deleteRequest);

                this.deleteUserService.setClientMessage(payload);
                this.deleteUserService.start();
                this.showLoadingOverlay();
            }
        });

        return button;
    }
}
