package com.vvalentim.server.commands.subscription;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.subscription.RequestSubscribedCategories;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.subscription.ResponseSubscribedCategories;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

import java.util.List;

public class ListSubscribedCategoriesCommand extends Command {
    private final RequestSubscribedCategories payload;

    public ListSubscribedCategoriesCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestSubscribedCategories) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.SUBSCRIPTION_LIST_CATEGORIES.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.username))
        ) {
            this.result = new ResponseUnauthorized(RequestType.SUBSCRIPTION_LIST_CATEGORIES.jsonKey);
            return;
        }

        User user = db.findUser(this.payload.username);

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.SUBSCRIPTION_LIST_CATEGORIES, ErrorType.USER_NOT_FOUND);
            return;
        }

        List<Integer> categories = db.fetchUserSubscribedCategories(this.payload.username);

        this.result = new ResponseSubscribedCategories(categories);
    }
}
