package com.vvalentim.gui.pages;

import atlantafx.base.theme.Styles;
import com.vvalentim.models.User;
import com.vvalentim.server.database.MemoryDatabase;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ListOnlineUsersPage extends AbstractPage {
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

    }

    private VBox createFields() {
        var title = new Text("Lista de usuários online");
        title.getStyleClass().addAll(Styles.TITLE_1, Styles.TEXT_BOLD);

        MemoryDatabase db = MemoryDatabase.getInstance();
        ObservableList<String> onlineUsers = db.getOnlineUsersObservable();
        ListView<String> onlineUsersListView = new ListView<>(onlineUsers);
        onlineUsersListView.setMinHeight(200);
        // onlineUsersListView.setCellFactory(user -> new UserListItem());

        var btnPrint = new Button("Print!");

        btnPrint.setOnMouseClicked(event -> {
            System.out.println(onlineUsers);
        });

        return new VBox(30, title, onlineUsersListView);
    }

    private static class UserListItem extends ListCell<String> {
        private final HBox root;
        private final Label usernameLabel;
        private final Label nameLabel;

        public UserListItem() {
            super();
            this.usernameLabel = new Label();
            this.nameLabel = new Label();
            this.root = new HBox(15, usernameLabel, nameLabel);
            this.root.setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        public void updateItem(String token, boolean empty) {
            super.updateItem(token, empty);

            if (empty) {
                setGraphic(null);
                return;
            }

            MemoryDatabase db = MemoryDatabase.getInstance();
            User user = db.findUser(token);

            if (user == null) {
                setGraphic(null);
                return;
            }

            this.usernameLabel.setText(user.getUsername());
            this.nameLabel.setText(user.getName());
            setGraphic(this.root);
        }
    }
}
