package com.vvalentim.protocol.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseNotificationSaved extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseNotificationSaved(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_SAVE.jsonKey);
        this.message = message;
    }
}
