package com.vvalentim.server.exceptions;

public class RequestTypeNotSupported extends Exception {
    public final String requestType;

    public RequestTypeNotSupported(String requestType) {
        this("RequestPayload type is not supported.", requestType);
    }

    public RequestTypeNotSupported(String message, String requestType) {
        super(message);
        this.requestType = requestType;
    }
}
