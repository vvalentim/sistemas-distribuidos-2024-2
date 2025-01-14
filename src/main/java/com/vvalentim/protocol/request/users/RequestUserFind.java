package com.vvalentim.protocol.request.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestUserFind extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("ra")
    public final String username;

    public RequestUserFind(
        @JsonProperty("token") String token,
        @JsonProperty("ra") String username
    ) {
        super(RequestType.USER_FIND);

        this.token = token;
        this.username = username;
    }

    @Override
    public String toString() {
        return "RequestUserFind{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean isValid() {
        return User.validateUsername(this.token);
    }
}
