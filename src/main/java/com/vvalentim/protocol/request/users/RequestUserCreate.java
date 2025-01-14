package com.vvalentim.protocol.request.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

final public class RequestUserCreate extends RequestPayload {
    public final String name;
    public final String username;
    public final String password;

    public RequestUserCreate(
        @JsonProperty("nome") String name,
        @JsonProperty("ra") String username,
        @JsonProperty("senha") String password
    ) {
        super(RequestType.USER_CREATE);

        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestUserCreate{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean isValid() {
        return
                User.validateName(name) &&
                User.validateUsername(username) &&
                User.validatePassword(password);
    }
}
