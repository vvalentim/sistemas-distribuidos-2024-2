package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.client.ConnectionHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClientConnectionPage extends AbstractPage {
    public static final String TITLE = "Conexão com o servidor";

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
    protected void init() {}

    private VBox createFields() {
        var title = new Text(ClientConnectionPage.TITLE);
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
        var btnConnect = new Button("_Conectar");
        btnConnect.setDefaultButton(true);
        btnConnect.setMnemonicParsing(true);

        btnConnect.setOnMouseClicked(event -> {
            ConnectionHandler handler = ConnectionHandler.getInstance();

            try {
                handler.open(addressField.getText(), Integer.parseInt(portField.getText()));

                this.changePage(Page.LOGIN_PAGE);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Failed to connect to the server: " + e.getMessage());
            }
        });

        return btnConnect;
    }
}
