package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseIncorrectCredentials extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseIncorrectCredentials() {
        super(ResponseStatus.UNAUTHORIZED, RequestType.LOGIN.jsonKey);

        this.message = ErrorType.INCORRECT_CREDENTIALS.message;
    }
}
