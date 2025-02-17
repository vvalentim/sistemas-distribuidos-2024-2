package com.vvalentim.protocol.response.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseSignupOnCategory extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseSignupOnCategory(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.SUBSCRIPTION_SIGNUP_CATEGORY.jsonKey);

        this.message = message;
    }
}
