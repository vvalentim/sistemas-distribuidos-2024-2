package com.vvalentim.protocol.request.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestUserUpdate extends RequestPayload {
    @JsonProperty("token")
    public final String token;

    @JsonProperty("usuario")
    public final User user;

    public RequestUserUpdate(
        @JsonProperty("token") String token,
        @JsonProperty("usuario") User user
    ) {
        super(RequestType.USER_UPDATE);

        this.token = token;
        this.user = user;
    }

    @Override
    public String toString() {
        return "RequestUserUpdate{" +
                "user=" + user +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean isValid() {
        if (this.user == null) {
            return false;
        }

        return
                User.validateUsername(this.user.getUsername()) &&
                User.validatePassword(this.user.getPassword()) &&
                User.validateName(this.user.getName());
    }
}
