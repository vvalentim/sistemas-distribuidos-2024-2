package com.vvalentim.protocol.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

import java.util.Objects;

public class ResponseGenericError extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseGenericError(
        @JsonProperty("status") int status,
        @JsonProperty("operacao") String requestType,
        @JsonProperty("mensagem") String message
    ) {
        super(
                Objects.requireNonNull(ResponseStatus.getFromCode(status)),
                requestType
        );

        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseGenericError{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
