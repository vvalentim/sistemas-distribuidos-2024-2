module com.vvalentim {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;

    exports com.vvalentim.models;

    exports com.vvalentim.client;

    exports com.vvalentim.server;
    exports com.vvalentim.server.exceptions;
    exports com.vvalentim.server.commands;

    exports com.vvalentim.protocol.request;
    exports com.vvalentim.protocol.request.authentication;
    exports com.vvalentim.protocol.request.users;
    exports com.vvalentim.protocol.request.notificationCategories;

    exports com.vvalentim.protocol.response;
    exports com.vvalentim.protocol.response.authentication;
    exports com.vvalentim.protocol.response.users;
    exports com.vvalentim.protocol.response.notificationCategories;
    exports com.vvalentim.protocol.response.errors;

    // exports com.vvalentim.gui.controllers;

    // opens com.vvalentim.gui;
    // opens com.vvalentim.gui.controllers;
}
