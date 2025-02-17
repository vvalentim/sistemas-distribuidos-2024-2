package com.vvalentim.protocol.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseNotificationFound extends ResponsePayload {
    @JsonProperty("aviso")
    public final Notification notification;

    public ResponseNotificationFound(
        @JsonProperty("aviso") Notification notification
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_FIND.jsonKey);
        this.notification = notification;
    }
}
