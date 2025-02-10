package com.vvalentim.protocol.response.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseLogin extends ResponsePayload {
    @JsonProperty("token")
    public final String token;

    public ResponseLogin(
        @JsonProperty("token") String token
    ) {
        super(ResponseStatus.OK, null);

        this.token = token;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "token='" + token + '\'' +
                ", status=" + status +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
