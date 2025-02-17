package com.vvalentim.protocol.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseNotificationDeleted extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseNotificationDeleted(
        @JsonProperty("mensagem") String mensagem
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_DELETE.jsonKey);
        this.message = mensagem;
    }
}
