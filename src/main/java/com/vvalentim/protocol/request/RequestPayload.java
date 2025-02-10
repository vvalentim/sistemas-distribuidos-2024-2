package com.vvalentim.protocol.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(
    // value = {"operacao"},
    ignoreUnknown = true
)
@JsonAutoDetect(isGetterVisibility = JsonAutoDetect.Visibility.NONE)
abstract public class RequestPayload {
    @JsonProperty("operacao")
    public final String requestType;

    public RequestPayload(RequestType requestType) {
        this.requestType = requestType.jsonKey;
    }

    public abstract boolean isValid();

    final public boolean isInvalid() {
        return !this.isValid();
    }
}
