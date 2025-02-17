package com.vvalentim.protocol.response.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUnsubscribeFromCategory extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseUnsubscribeFromCategory(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.SUBSCRIPTION_UNSUBSCRIBE_CATEGORY.jsonKey);

        this.message = message;
    }
}
