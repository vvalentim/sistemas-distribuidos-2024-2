package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUnprocessableContent extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseUnprocessableContent() {
        super(ResponseStatus.UNAUTHORIZED, null);

        this.message = ErrorType.UNPROCESSABLE_CONTENT.message;
    }
}
