package com.vvalentim.protocol.request.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

final public class RequestUserSignup extends RequestPayload {
    public final String name;
    public final String username;
    public final String password;

    public RequestUserSignup(
        @JsonProperty("nome") String name,
        @JsonProperty("ra") String username,
        @JsonProperty("senha") String password
    ) {
        super(RequestType.USER_SIGNUP);

        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestUserSignup{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean validate() {
        return
                User.validateName(name) &&
                User.validateUsername(username) &&
                User.validatePassword(password);
    }
}
