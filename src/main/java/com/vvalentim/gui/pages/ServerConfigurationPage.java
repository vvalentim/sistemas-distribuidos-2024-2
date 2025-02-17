package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.google.common.net.InetAddresses;
import com.vvalentim.client.ConnectionHandler;
import com.vvalentim.client.services.ServerConnectionService;
import com.vvalentim.gui.ServerLauncher;
import com.vvalentim.gui.layout.ErrorAlert;
import com.vvalentim.models.User;
import com.vvalentim.server.Server;
import com.vvalentim.server.database.MemoryDatabase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ServerConfigurationPage extends AbstractPage {
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
    public void init() {}

    private VBox createFields() {
        var title = new Text("Configuração do servidor");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        var labelAddressField = new Label("Endereço IP");
        var addressField = new TextField();
        addressField.setPrefWidth(200);
        addressField.setPromptText("127.0.0.1");
        var group1 = new VBox(labelAddressField, addressField);

        var labelPortField = new Label("Porta");
        var portField = new TextField();
        portField.setPrefWidth(200);
        portField.setPromptText("20000");
        var group2 = new VBox(labelPortField, portField);

        var btnConnect = createConnectButton(addressField, portField);

        return new VBox(30, title, group1, group2, btnConnect);
    }

    private Button createConnectButton(TextField addressField, TextField portField) {
        var btnConnect = new Button("Iniciar");
        btnConnect.setDefaultButton(true);
        btnConnect.setMnemonicParsing(true);

        btnConnect.setOnMouseClicked(event -> {
            String address = addressField.getText();
            String portText = portField.getText();
            int port = !portText.isEmpty() && portText.matches("^[0-9]*$") ? Integer.parseInt(portText) : 0;

            if (!InetAddresses.isInetAddress(address) || port <= 0 || port > 65535) {
                ErrorAlert alert = new ErrorAlert("Endereço IP ou porta inválido(s).");
                alert.showAndWait();
                return;
            }

            ServerLauncher.server = new Server(address, port);
            ServerLauncher.server.start();

            this.changePage(Page.LIST_ONLINE_USERS_PAGE);
        });

        return btnConnect;
    }
}
