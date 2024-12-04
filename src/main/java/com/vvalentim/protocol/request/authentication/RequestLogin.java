package com.vvalentim.protocol.request.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

final public class RequestLogin extends RequestPayload {
    public final String username;
    public final String password;

    public RequestLogin(
        @JsonProperty("ra") String username,
        @JsonProperty("senha") String password
    ) {
        super(RequestType.LOGIN);

        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestLogin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean validate() {
        return User.validateUsername(username) && User.validatePassword(password);
    }
}
