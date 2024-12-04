package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseInvalidOperation extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseInvalidOperation(String requestType) {
        super(ResponseStatus.UNAUTHORIZED, requestType);

        this.message = ErrorType.INVALID_OPERATION.message;
    }
}
