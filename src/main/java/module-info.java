module com.vvalentim {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;

    requires MaterialFX;
    requires VirtualizedFX;

    exports com.vvalentim.client;
    exports com.vvalentim.client.controllers;
    exports com.vvalentim.exceptions;
    exports com.vvalentim.server;
    exports com.vvalentim.server.commands;
    exports com.vvalentim.protocol.request;
    exports com.vvalentim.protocol.request.authentication;
    exports com.vvalentim.protocol.response;
    exports com.vvalentim.protocol.response.authentication;
    exports com.vvalentim.protocol.response.errors;

    opens com.vvalentim.client;
    opens com.vvalentim.client.controllers;
}
