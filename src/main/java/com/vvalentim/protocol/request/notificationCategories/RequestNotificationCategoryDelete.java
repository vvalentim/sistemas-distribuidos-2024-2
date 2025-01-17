package com.vvalentim.protocol.request.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationCategoryDelete extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("id")
    public final int categoryId;

    public RequestNotificationCategoryDelete(
        @JsonProperty("token") String token,
        @JsonProperty("id") int id
    ) {
        super(RequestType.NOTIFICATION_CATEGORY_DELETE);

        this.token = token;
        this.categoryId = id;
    }

    @Override
    public boolean isValid() {
        return
                User.validateUsername(this.token) &&
                NotificationCategory.validateExistingId(this.categoryId);
    }
}
