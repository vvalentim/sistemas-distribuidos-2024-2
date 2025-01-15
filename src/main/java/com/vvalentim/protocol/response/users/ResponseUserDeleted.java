package com.vvalentim.protocol.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUserDeleted extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseUserDeleted(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.USER_DELETE.jsonKey);
        this.message = message;
    }
}
