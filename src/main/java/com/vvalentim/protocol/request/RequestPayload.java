package com.vvalentim.protocol.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"operacao"}, ignoreUnknown = true)
abstract public class RequestPayload {
    public final String requestType;

    public RequestPayload(RequestType requestType) {
        this.requestType = requestType.jsonKey;
    }

    public abstract boolean isValid();

    final public boolean isInvalid() {
        return !this.isValid();
    }
}
