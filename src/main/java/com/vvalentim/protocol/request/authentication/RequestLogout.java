package com.vvalentim.protocol.request.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;

final public class RequestLogout extends RequestPayload {
    public final String token;

    public RequestLogout(
        @JsonProperty("token") String token
    ) {
        super(RequestType.LOGOUT);

        this.token = token;
    }

    @Override
    public String toString() {
        return "RequestLogout{" +
                "token='" + token + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }

    @Override
    public boolean validate() {
        System.out.println("validou logout");
        return true;
    }
}
