package com.vvalentim.server.exceptions;

public class UnprocessableContentException extends Exception {
    public UnprocessableContentException() {
        super("Unable to parse JSON contents.");
    }

    public UnprocessableContentException(String message) {
        super(message);
    }
}
