package com.vvalentim.protocol.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

import java.util.List;

public class ResponseNotificationList extends ResponsePayload {
    @JsonProperty("avisos")
    public final List<Notification> notifications;

    public ResponseNotificationList(
        @JsonProperty("avisos") List<Notification> notifications
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_LIST.jsonKey);
        this.notifications = notifications;
    }
}
