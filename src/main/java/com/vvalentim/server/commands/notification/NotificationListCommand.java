package com.vvalentim.server.commands.notification;

import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notification.RequestNotificationList;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notification.ResponseNotificationList;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

import java.util.List;

public class NotificationListCommand extends Command {
    private final RequestNotificationList payload;

    public NotificationListCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationList) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_LIST.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);

        if (!isOnline) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_LIST.jsonKey);
            return;
        }

        List<Notification> notifications;

        if (this.payload.categoryId != 0) {
            if (db.findNotificationCategory(this.payload.categoryId) == null) {
                this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_LIST, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
                return;
            }

            notifications = db.fetchNotificationsWithCategory(this.payload.categoryId);
        } else {
            notifications = db.fetchAllNotifications();
        }

        this.result = new ResponseNotificationList(notifications);
    }
}
