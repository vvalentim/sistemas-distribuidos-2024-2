package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUserAlreadyLoggedIn extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseUserAlreadyLoggedIn(String requestType) {
        super(ResponseStatus.UNAUTHORIZED, requestType);

        this.message = ErrorType.USER_ALREADY_LOGGED_IN.message;
    }
}