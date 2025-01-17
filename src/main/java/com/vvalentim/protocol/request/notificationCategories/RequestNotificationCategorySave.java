package com.vvalentim.protocol.request.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationCategorySave extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("categoria")
    public final NotificationCategory category;

    public RequestNotificationCategorySave(
        @JsonProperty("token") String token,
        @JsonProperty("categoria") NotificationCategory category
    ) {
        super(RequestType.NOTIFICATION_CATEGORY_SAVE);

        this.token = token;
        this.category = category;
    }

    @Override
    public boolean isValid() {
        if (this.category == null) {
            return false;
        }

        return
                User.validateUsername(this.token) &&
                NotificationCategory.validateId(this.category.getId()) &&
                NotificationCategory.validateName(this.category.getName());
    }
}
