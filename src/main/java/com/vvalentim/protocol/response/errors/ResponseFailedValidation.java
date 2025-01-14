package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseFailedValidation extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseFailedValidation(String requestType) {
        super(ResponseStatus.UNAUTHORIZED, requestType);

        this.message = ErrorType.FAILED_VALIDATION.message;
    }
}
