package com.vvalentim.server.commands.notificationCategories;

import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryFind;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryFind;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class NotificationCategoryFindCommand extends Command {
    private final RequestNotificationCategoryFind payload;

    public NotificationCategoryFindCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationCategoryFind) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_CATEGORY_FIND.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);

        if (!isOnline) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_CATEGORY_FIND.jsonKey);
            return;
        }

        NotificationCategory category = db.findNotificationCategory(this.payload.categoryId);

        if (category == null) {
            this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_CATEGORY_FIND, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
            return;
        }

        this.result = new ResponseNotificationCategoryFind(category);
    }
}
