package com.vvalentim.protocol.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract public class ResponsePayload {
    @JsonProperty("status")
    public final int status;

    @JsonProperty("operacao")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final String requestType;

    public ResponsePayload(ResponseStatus responseStatus, String requestType) {
        this.status = responseStatus.code;
        this.requestType = requestType;
    }
}
