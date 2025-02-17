package com.vvalentim.protocol.request.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.Notification;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.SaveNotificationDto;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationSave extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("aviso")
    public final SaveNotificationDto notification;

    public RequestNotificationSave(
        @JsonProperty("token") String token,
        @JsonProperty("aviso") SaveNotificationDto notification
    ) {
        super(RequestType.NOTIFICATION_SAVE);

        this.token = token;
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "RequestNotificationSave{" +
                "token='" + token + '\'' +
                ", notification=" + notification +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean isValid() {
        if (this.notification == null) {
            return false;
        }

        return
                User.validateUsername(this.token) &&
                Notification.validateId(this.notification.id) &&
                NotificationCategory.validateExistingId(this.notification.categoryId) &&
                Notification.validateTitle(this.notification.title) &&
                Notification.validateDescription(this.notification.description);
    }
}
