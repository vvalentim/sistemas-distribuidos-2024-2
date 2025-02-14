package com.vvalentim.gui.layout;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorAlert extends Alert {
    public ErrorAlert(String message) {
        this(message, null);
    }

    public ErrorAlert(String message, Exception exception) {
        super(AlertType.ERROR);

        this.setTitle("Erro!");
        this.setHeaderText("Erro!");
        this.setContentText(message);

        if (exception != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);

            exception.printStackTrace(printWriter);

            TextArea textArea = new TextArea(stringWriter.toString());
            textArea.setEditable(false);
            textArea.setWrapText(false);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane content = new GridPane();
            content.setMaxWidth(Double.MAX_VALUE);
            content.add(new Label("Stacktrace:"), 0, 0);
            content.add(textArea, 0, 1);

            this.getDialogPane().setExpandableContent(content);
        }
    }
}
