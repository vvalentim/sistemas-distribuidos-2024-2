package com.vvalentim.server.commands.notification;

import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notification.RequestNotificationFind;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notification.ResponseNotificationFound;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class NotificationFindCommand extends Command {
    private final RequestNotificationFind payload;

    public NotificationFindCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationFind) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_FIND.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);

        if (!isOnline) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_FIND.jsonKey);
            return;
        }

        Notification notification = db.findNotification(this.payload.id);

        if (notification == null) {
            this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_FIND, ErrorType.NOTIFICATION_NOT_FOUND);
            return;
        }

        this.result = new ResponseNotificationFound(notification);
    }
}
