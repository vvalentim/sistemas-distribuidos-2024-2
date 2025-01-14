package com.vvalentim.protocol.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

import java.util.List;

public class ResponseUserList extends ResponsePayload {
    @JsonProperty("usuarios")
    public final List<User> users;

    public ResponseUserList(
        @JsonProperty("usuarios") List<User> users
    ) {
        super(ResponseStatus.CREATED, RequestType.USER_LIST.jsonKey);
        this.users = users;
    }
}
