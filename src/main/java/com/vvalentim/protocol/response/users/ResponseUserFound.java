package com.vvalentim.protocol.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseUserFound extends ResponsePayload {
    @JsonProperty("usuario")
    public final User user;

    public ResponseUserFound(
        @JsonProperty("usuario") User user
    ) {
        super(ResponseStatus.CREATED, RequestType.USER_FIND.jsonKey);
        this.user = user;
    }

    @Override
    public String toString() {
        return "ResponseUserFound{" +
                "user=" + user +
                ", status=" + status +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
