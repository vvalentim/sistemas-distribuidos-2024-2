package com.vvalentim.protocol.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUserCreated extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseUserCreated(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.USER_CREATE.jsonKey);
        this.message = message;
    }
}
