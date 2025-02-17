package com.vvalentim.protocol.response.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseNotificationCategoryFound extends ResponsePayload {
    @JsonProperty("categoria")
    public final NotificationCategory category;

    public ResponseNotificationCategoryFound(
        @JsonProperty("categoria") NotificationCategory category
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_CATEGORY_FIND.jsonKey);
        this.category = category;
    }
}
