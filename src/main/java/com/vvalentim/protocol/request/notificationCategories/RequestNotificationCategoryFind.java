package com.vvalentim.protocol.request.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationCategoryFind extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("id")
    public final int categoryId;

    public RequestNotificationCategoryFind(
        @JsonProperty("token") String token,
        @JsonProperty("id") int categoryId
    ) {
        super(RequestType.NOTIFICATION_CATEGORY_FIND);

        this.token = token;
        this.categoryId = categoryId;
    }

    @Override
    public boolean isValid() {
        // return NotificationCategory.validateExistingId(this.categoryId);
        return true;
    }
}
