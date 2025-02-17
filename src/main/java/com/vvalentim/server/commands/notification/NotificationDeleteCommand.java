package com.vvalentim.server.commands.notification;

import com.vvalentim.models.Notification;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notification.RequestNotificationDelete;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notification.ResponseNotificationDeleted;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class NotificationDeleteCommand extends Command {
    private final RequestNotificationDelete payload;

    public NotificationDeleteCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationDelete) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_DELETE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(this.payload.token);

        if (!isOnline || !isSuperUser) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_DELETE.jsonKey);
            return;
        }

        Notification notification = db.findNotification(this.payload.id);

        if (notification == null) {
            this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_DELETE, ErrorType.NOTIFICATION_NOT_FOUND);
            return;
        }

        db.deleteNotification(this.payload.id);

        this.result = new ResponseNotificationDeleted("Exclusão realizada com sucesso.");
    }
}
