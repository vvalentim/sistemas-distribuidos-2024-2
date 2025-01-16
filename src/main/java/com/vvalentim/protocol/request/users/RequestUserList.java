package com.vvalentim.protocol.request.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

public class RequestUserList extends RequestPayload {
    public final String token;

    public RequestUserList(
        @JsonProperty("token") String token
    ) {
        super(RequestType.USER_LIST);

        this.token = token;
    }

    @Override
    public String toString() {
        return "RequestUserList{" +
                "token='" + token + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean isValid() {
        // return User.validateUsername(token);
        return true;
    }
}
