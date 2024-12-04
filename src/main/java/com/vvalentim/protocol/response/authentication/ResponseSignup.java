package com.vvalentim.protocol.response.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseSignup extends ResponsePayload {
    public final String message;

    public ResponseSignup(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.USER_SIGNUP.jsonKey);
        this.message = message;
    }
}
