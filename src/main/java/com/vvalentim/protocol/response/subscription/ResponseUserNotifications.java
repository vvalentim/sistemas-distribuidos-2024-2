package com.vvalentim.protocol.response.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

import java.util.List;

public class ResponseUserNotifications extends ResponsePayload {
    @JsonProperty("avisos")
    public final List<Notification> notifications;

    public ResponseUserNotifications(
        @JsonProperty("avisos") List<Notification> notifications
    ) {
        super(ResponseStatus.CREATED, RequestType.SUBSCRIPTION_USER_NOTIFICATIONS.jsonKey);

        this.notifications = notifications;
    }
}
