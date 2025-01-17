package com.vvalentim.protocol.request.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestNotificationCategoryList extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    public RequestNotificationCategoryList(
        @JsonProperty("token") String token
    ) {
        super(RequestType.NOTIFICATION_CATEGORY_LIST);

        this.token = token;
    }

    @Override
    public boolean isValid() {
        return User.validateUsername(this.token);
    }
}
