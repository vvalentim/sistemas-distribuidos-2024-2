package com.vvalentim.client.controllers;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class LoginController {
    @FXML
    private MFXTextField username;

    @FXML
    private MFXPasswordField password;

    @FXML
    public void initialize() {
        username.setTextLimit(20);
        password.setTextLimit(20);
    }

    @FXML
    private void submitLogin() {

    }
}
