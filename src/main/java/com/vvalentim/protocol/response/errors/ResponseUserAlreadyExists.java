package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUserAlreadyExists extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseUserAlreadyExists() {
        super(ResponseStatus.UNAUTHORIZED, RequestType.USER_SIGNUP.jsonKey);

        this.message = ErrorType.USER_ALREADY_EXISTS.message;
    }
}
