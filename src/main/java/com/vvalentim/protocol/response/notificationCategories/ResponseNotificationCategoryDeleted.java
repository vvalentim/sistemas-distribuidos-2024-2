package com.vvalentim.protocol.response.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

public class ResponseNotificationCategoryDeleted extends ResponsePayload {
    @JsonProperty("mensagem")
    public final String message;

    public ResponseNotificationCategoryDeleted(
        @JsonProperty("mensagem") String message
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_CATEGORY_DELETE.jsonKey);
        this.message = message;
    }
}
