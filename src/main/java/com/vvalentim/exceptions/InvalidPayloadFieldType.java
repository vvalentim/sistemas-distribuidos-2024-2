package com.vvalentim.exceptions;

public class InvalidPayloadFieldType extends RuntimeException {
    public final String requestType;

    public InvalidPayloadFieldType(String requestType) {
        this("The request payload contains one or more fields with invalid content types.", requestType);
    }

    public InvalidPayloadFieldType(String message, String requestType) {
        super(message);
        this.requestType = requestType;
    }
}
