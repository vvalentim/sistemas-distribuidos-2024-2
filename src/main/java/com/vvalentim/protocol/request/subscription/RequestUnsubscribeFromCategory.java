package com.vvalentim.protocol.request.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestUnsubscribeFromCategory extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("ra")
    public final String username;

    @JsonProperty("categoria")
    public final int categoryId;

    public RequestUnsubscribeFromCategory(
        @JsonProperty("token") String token,
        @JsonProperty("ra") String username,
        @JsonProperty("categoria") int categoryId
    ) {
        super(RequestType.SUBSCRIPTION_UNSUBSCRIBE_CATEGORY);

        this.token = token;
        this.username = username;
        this.categoryId = categoryId;
    }

    @Override
    public boolean isValid() {
        return
                User.validateUsername(this.token) &&
                User.validateUsername(this.username) &&
                NotificationCategory.validateExistingId(this.categoryId);
    }
}
