package com.vvalentim.protocol.response.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.ResponseStatus;

import java.util.List;

public class ResponseSubscribedCategories extends ResponsePayload {
    @JsonProperty("categorias")
    public final List<Integer> categories;

    public ResponseSubscribedCategories(
        @JsonProperty("categorias") List<Integer> categories
    ) {
        super(ResponseStatus.CREATED, RequestType.SUBSCRIPTION_LIST_CATEGORIES.jsonKey);

        this.categories = categories;
    }
}
