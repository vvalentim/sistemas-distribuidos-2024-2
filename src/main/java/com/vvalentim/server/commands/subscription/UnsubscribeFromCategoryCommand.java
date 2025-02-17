package com.vvalentim.server.commands.subscription;

import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.subscription.RequestUnsubscribeFromCategory;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.subscription.ResponseUnsubscribeFromCategory;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class UnsubscribeFromCategoryCommand extends Command {
    private final RequestUnsubscribeFromCategory payload;

    public UnsubscribeFromCategoryCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestUnsubscribeFromCategory) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.SUBSCRIPTION_UNSUBSCRIBE_CATEGORY.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.username))
        ) {
            this.result = new ResponseUnauthorized(RequestType.SUBSCRIPTION_UNSUBSCRIBE_CATEGORY.jsonKey);
            return;
        }

        User user = db.findUser(this.payload.username);

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.SUBSCRIPTION_UNSUBSCRIBE_CATEGORY, ErrorType.USER_NOT_FOUND);
            return;
        }

        NotificationCategory category = db.findNotificationCategory(this.payload.categoryId);

        if (category == null) {
            this.result = new ResponseResourceNotFound(RequestType.SUBSCRIPTION_UNSUBSCRIBE_CATEGORY, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
            return;
        }

        db.unsubscribeFromCategory(this.payload.username, this.payload.categoryId);

        this.result = new ResponseUnsubscribeFromCategory("Descadastro realizado com sucesso.");
    }
}
