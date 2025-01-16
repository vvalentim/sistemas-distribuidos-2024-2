package com.vvalentim.server.commands.notificationCategories;

import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryList;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryList;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class NotificationCategoryListCommand extends Command {
    private final RequestNotificationCategoryList payload;

    public NotificationCategoryListCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationCategoryList) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_CATEGORY_LIST.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);

        if (!isOnline) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_CATEGORY_LIST.jsonKey);
            return;
        }

        this.result = new ResponseNotificationCategoryList(db.fetchAllNotificationCategories());
    }
}
