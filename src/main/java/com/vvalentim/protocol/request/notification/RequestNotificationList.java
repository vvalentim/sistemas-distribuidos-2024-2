package com.vvalentim.protocol.request.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationList extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("categoria")
    public final int categoryId;

    public RequestNotificationList(
        @JsonProperty("token") String token,
        @JsonProperty("categoria") int categoryId
    ) {
        super(RequestType.NOTIFICATION_LIST);

        this.token = token;
        this.categoryId = categoryId;
    }

    @Override
    public boolean isValid() {
        return
                User.validateUsername(this.token) &&
                NotificationCategory.validateExistingId(this.categoryId);
    }
}
