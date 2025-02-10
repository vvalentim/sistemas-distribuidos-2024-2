package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.users.RequestUserFind;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.users.ResponseUserFound;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HomePage extends AbstractPage {
    public static final String TITLE = "Meu cadastro";

    private MessageService fetchUserService;
    private MessageService updateUserService;
    private MessageParser parser;

    private TextField nameField;
    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    protected void init() {
        this.parser = new MessageParser();
        this.fetchUserService = new MessageService(ConnectionHandler.getInstance());
        this.updateUserService = new MessageService(ConnectionHandler.getInstance());

        var payloadInitialFetch = new RequestUserFind("2099284", "2099284");

        this.fetchUserService.setClientMessage(parser.serialize(payloadInitialFetch));

        this.fetchUserService.setOnSucceeded(event -> {
            String response = this.fetchUserService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseUserFound.class);

            if (payload instanceof  ResponseUserFound) {
                System.out.println("SUCCESS!");

                var user = (User) ((ResponseUserFound) payload).user;

                this.nameField.setText(user.getName());
                this.usernameField.setText(user.getUsername());
            } else {
                System.out.println("ERROR!");
            }
        });

        this.fetchUserService.setOnFailed(event -> {
            System.out.println("no connection?");
        });

        this.updateUserService.setOnSucceeded(event -> {

        });

        this.fetchUserService.start();
    }

    @Override
    protected void build() {
        var body = this.createBodyWrapper();
        body.setPadding(new Insets(0, 0, 0, 0));
        body.setFillHeight(true);

        this.getStyleClass().addAll();
        this.getChildren().setAll(body);
    }

    private HBox createBodyWrapper() {
        return new HBox(15, createSidebar(), createContent());
    }

    private VBox createContent() {
        var title = new Text(HomePage.TITLE);
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        var labelName = new Label("Nome");
        nameField = new TextField();
        nameField.setPrefWidth(200);
        nameField.setPromptText("1234567");
        var group1 = new VBox(labelName, nameField);

        var labelUsername = new Label("RA do usuário");
        usernameField = new TextField();
        usernameField.setPrefWidth(200);
        usernameField.setEditable(false);
        var group2 = new VBox(labelUsername, usernameField);

        var labelPasswordField = new Label("Senha");
        passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        var group3 = new VBox(labelPasswordField, passwordField);

        return new VBox(30, title, group1, group2, group3);
    }

    private VBox createSidebar() {
        var navCategoriesPage = new Button("_Lista de categorias");
        navCategoriesPage.setMnemonicParsing(true);
        navCategoriesPage.setMaxWidth(Double.MAX_VALUE);

        navCategoriesPage.setOnMouseClicked(event -> {
            System.out.println("Change to categories page");
        });

        var navLogout = new Button("_Logout");
        navLogout.setMnemonicParsing(true);
        navLogout.setMaxWidth(Double.MAX_VALUE);

        navLogout.setOnMouseClicked(event -> {
            System.out.println("Logout");
        });

        VBox sidebar = new VBox(15, navCategoriesPage, navLogout);
        sidebar.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        sidebar.setPadding(new Insets(25, 10, 25, 10));
        sidebar.setFillWidth(true);
        sidebar.setPrefWidth(200);

        return sidebar;
    }
}
