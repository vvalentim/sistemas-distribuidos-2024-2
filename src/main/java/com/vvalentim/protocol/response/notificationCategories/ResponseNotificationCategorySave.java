package com.vvalentim.protocol.response.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseNotificationCategorySave extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseNotificationCategorySave(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_CATEGORY_SAVE.jsonKey);
        this.message = message;
    }
}
