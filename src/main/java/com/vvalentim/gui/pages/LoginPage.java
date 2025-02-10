package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.MessageParser;
import com.vvalentim.client.services.MessageService;
import com.vvalentim.protocol.request.authentication.RequestLogin;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.authentication.ResponseLogin;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginPage extends AbstractPage {
    public static final String TITLE = "Login";

    private MessageParser parser;

    private MessageService loginService;

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
    protected void init() {
        this.parser = new MessageParser();
        this.loginService = new MessageService(ConnectionHandler.getInstance());

        this.loginService.setOnSucceeded(event -> {
            String response = this.loginService.getValue();
            ResponsePayload payload = parser.deserialize(response, ResponseLogin.class);

            if (payload instanceof ResponseLogin) {
                // System.out.println("SUCCESS!");
                this.changePage(Page.HOME_PAGE);
            } else {
                // System.out.println("ERROR!");
            }
        });
    }

    private VBox createFields() {
        var title = new Text(LoginPage.TITLE);
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        var labelUsername = new Label("RA do usuário");
        var usernameField = new TextField();
        usernameField.setPrefWidth(200);
        usernameField.setPromptText("1234567");
        var group1 = new VBox(labelUsername, usernameField);

        var labelPasswordField = new Label("Senha");
        var passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        var group2 = new VBox(labelPasswordField, passwordField);

        var btnLogin = this.createLoginButton(usernameField, passwordField);

        var btnSignup = this.createSigninButton();

        return new VBox(30, title, group1, group2, btnLogin, btnSignup);
    }

    private Button createLoginButton(TextField username, PasswordField password) {
        var btnLogin = new Button("_Entrar");
        btnLogin.setDefaultButton(true);
        btnLogin.setMnemonicParsing(true);

        btnLogin.setOnMouseClicked(event -> this.attemptLogin(username.getText(), password.getText()));

        return btnLogin;
    }

    private Button createSigninButton() {
        var btnSignup = new Button("_Cadastrar-se");

        btnSignup.setOnMouseClicked(event -> this.changePage(Page.SIGNUP_PAGE));

        return btnSignup;
    }

    private void attemptLogin(String username, String password) {
        if (this.loginService.isRunning()) {
            return;
        }

        if (this.loginService.getState() != Worker.State.READY) {
            this.loginService.reset();
        }

        try {
            RequestLogin loginRequest = new RequestLogin(username, password);
            String payload = this.parser.serialize(loginRequest);

            this.loginService.setClientMessage(payload);

            this.loginService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
