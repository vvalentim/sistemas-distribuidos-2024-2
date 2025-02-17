package com.vvalentim.protocol.request.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestSubscribedCategories extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("ra")
    public final String username;

    public RequestSubscribedCategories(
        @JsonProperty("token") String token,
        @JsonProperty("ra") String username
    ) {
        super(RequestType.SUBSCRIPTION_LIST_CATEGORIES);

        this.token = token;
        this.username = username;
    }

    @Override
    public boolean isValid() {
        return
                User.validateUsername(this.token) &&
                User.validateUsername(this.username);
    }
}
