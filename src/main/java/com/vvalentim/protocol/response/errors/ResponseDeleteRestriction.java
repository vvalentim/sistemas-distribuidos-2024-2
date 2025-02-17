package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseDeleteRestriction extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseDeleteRestriction(RequestType requestType, ErrorType error) {
        super(ResponseStatus.UNAUTHORIZED, requestType.jsonKey);
        this.message = error.message;
    }
}
