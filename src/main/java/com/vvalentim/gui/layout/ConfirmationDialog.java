package com.vvalentim.gui.layout;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class ConfirmationDialog extends Alert {
    public ConfirmationDialog(String message) {
        super(AlertType.CONFIRMATION);

        this.setTitle("Confirmação");
        this.setHeaderText("Essa ação não pode ser desfeita.");
        this.setContentText(message);

        Button btnOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        Button btnCancel = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);

        btnOk.setText("Confirmar");
        btnCancel.setText("Cancelar");
    }
}
