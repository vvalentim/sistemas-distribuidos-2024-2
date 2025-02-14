package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.gui.layout.InfoAlert;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.users.RequestUserCreate;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseGenericError;
import com.vvalentim.protocol.response.users.ResponseUserCreated;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SignupPage extends AbstractPage {
    private MessageParser parser;
    private MessageService signupService;

    @Override
    protected void build() {
        var body = this.createFields();
        body.setPadding(new Insets(40, 40, 40, 40));
        body.setAlignment(Pos.TOP_CENTER);
        body.setFillWidth(false);

        this.getStyleClass().addAll();
        this.getChildren().setAll(body);
    }

    @Override
    public void init() {
        this.parser = new MessageParser();
        this.signupService = new MessageService(ConnectionHandler.getInstance());

        this.signupService.setOnSucceeded(event -> {
            String response = this.signupService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseUserCreated.class);

            if (payload instanceof ResponseUserCreated) {
                ResponseUserCreated signupPayload = (ResponseUserCreated) payload;
                InfoAlert alert = new InfoAlert(signupPayload.message);
                alert.showAndWait();
                this.changePage(Page.LOGIN_PAGE);
                return;
            }

            ResponseGenericError errorPayload = (ResponseGenericError) payload;
            ErrorAlert alert = new ErrorAlert(errorPayload.message);
            alert.showAndWait();
            this.hideLoadingOverlay();
        });
    }

    private VBox createFields() {
        var title = new Text("Cadastro");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        var labelName = new Label("Nome");
        var nameField = new TextField();
        nameField.setPrefWidth(200);
        nameField.setPromptText("NOME");
        var group1 = new VBox(labelName, nameField);

        var labelUsername = new Label("RA do usuário");
        var usernameField = new TextField();
        usernameField.setPrefWidth(200);
        usernameField.setPromptText("1234567");
        var group2 = new VBox(labelUsername, usernameField);

        var labelPasswordField = new Label("Senha");
        var passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        var group3 = new VBox(labelPasswordField, passwordField);

        var btnSignup = this.createSignupButton(nameField, usernameField, passwordField);
        var btnCancel = new Button("Voltar");

        btnCancel.setOnMouseClicked(event -> {
            this.changePage(Page.LOGIN_PAGE);
        });

        return new VBox(25, title, group1, group2, group3, btnSignup, btnCancel);
    }

    private Button createSignupButton(
        TextField nameField,
        TextField usernameField,
        PasswordField passwordField
    ) {
        var btnSignup = new Button("Cadastrar-se");
        btnSignup.setDefaultButton(true);

        btnSignup.setOnMouseClicked(event -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (this.signupService.isRunning()) {
                return;
            }

            if (
                !User.validateName(name) ||
                !User.validateUsername(username) ||
                !User.validatePassword(password)
            ) {
                ErrorAlert alert = new ErrorAlert("Dados inválidos.");
                alert.showAndWait();
                return;
            }

            if (this.signupService.getState() != Worker.State.READY) {
                this.signupService.reset();
            }

            try {
                RequestUserCreate signupRequest = new RequestUserCreate(name, username, password);
                String payload = this.parser.serialize(signupRequest);

                this.signupService.setClientMessage(payload);
                this.signupService.start();
                this.showLoadingOverlay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return btnSignup;
    }
}
