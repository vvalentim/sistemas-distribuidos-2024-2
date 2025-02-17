package com.vvalentim.server.commands.subscription;

import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.subscription.RequestSignupOnCategory;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.subscription.ResponseSignupOnCategory;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class SignupOnCategoryCommand extends Command {
    private final RequestSignupOnCategory payload;

    public SignupOnCategoryCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestSignupOnCategory) payload;
    }

    @Override
    public void execute() {
        if (payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.SUBSCRIPTION_SIGNUP_CATEGORY.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.username))
        ) {
            this.result = new ResponseUnauthorized(RequestType.SUBSCRIPTION_SIGNUP_CATEGORY.jsonKey);
            return;
        }

        User user = db.findUser(this.payload.username);

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.SUBSCRIPTION_SIGNUP_CATEGORY, ErrorType.USER_NOT_FOUND);
            return;
        }

        NotificationCategory category = db.findNotificationCategory(this.payload.categoryId);

        if (category == null) {
            this.result = new ResponseResourceNotFound(RequestType.SUBSCRIPTION_SIGNUP_CATEGORY, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
            return;
        }

        db.subscribeOnCategory(this.payload.username, this.payload.categoryId);

        this.result = new ResponseSignupOnCategory("Cadastro em categoria realizado com sucesso.");
    }
}
