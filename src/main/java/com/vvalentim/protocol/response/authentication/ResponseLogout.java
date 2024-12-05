package com.vvalentim.protocol.response.authentication;

import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseLogout extends ResponsePayload {
    public ResponseLogout() {
        super(ResponseStatus.OK, null);
    }
}
