package com.vvalentim.protocol.response.notificationCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

import java.util.List;

public class ResponseNotificationCategoryList extends ResponsePayload {
    @JsonProperty("categorias")
    public final List<NotificationCategory> categories;

    public ResponseNotificationCategoryList(
        @JsonProperty("categorias") List<NotificationCategory> categories
    ) {
        super(ResponseStatus.CREATED, RequestType.NOTIFICATION_CATEGORY_LIST.jsonKey);
        this.categories = categories;
    }
}
