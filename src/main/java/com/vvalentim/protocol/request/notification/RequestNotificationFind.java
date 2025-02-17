package com.vvalentim.protocol.request.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.Notification;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationFind extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("id")
    public final int id;

    public RequestNotificationFind(
            @JsonProperty("token") String token,
            @JsonProperty("id") int id
    ) {
        super(RequestType.NOTIFICATION_FIND);

        this.token = token;
        this.id = id;
    }

    @Override
    public boolean isValid() {
        return
                User.validateUsername(this.token) &&
                Notification.validateExistingId(this.id);
    }
}
