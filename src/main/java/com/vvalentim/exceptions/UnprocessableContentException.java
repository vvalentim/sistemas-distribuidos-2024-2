package com.vvalentim.exceptions;

public class UnprocessableContentException extends Exception {
    public UnprocessableContentException() {
        super("Unable to parse JSON contents.");
    }

    public UnprocessableContentException(String message) {
        super(message);
    }
}
