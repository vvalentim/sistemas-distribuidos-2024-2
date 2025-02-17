package com.vvalentim.server.commands.notification;

import com.vvalentim.models.Notification;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notification.RequestNotificationSave;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notification.ResponseNotificationSaved;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class NotificationSaveCommand extends Command {
    private final RequestNotificationSave payload;

    public NotificationSaveCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationSave) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_SAVE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(this.payload.token);

        if (!isOnline || !isSuperUser) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_SAVE.jsonKey);
            return;
        }

        NotificationCategory category = db.findNotificationCategory(this.payload.notification.categoryId);
        Notification notification = null;

        if (category == null) {
            this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_SAVE, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
            return;
        }

        if (this.payload.notification.id != 0) {
            notification = db.findNotification(this.payload.notification.id);

            // If an id is specified but the notification has not been created yet
            if (notification == null) {
                this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_SAVE, ErrorType.NOTIFICATION_NOT_FOUND);
                return;
            }
        } else {
            notification = new Notification(
              category,
              this.payload.notification.title,
              this.payload.notification.description
            );
        }

        db.saveNotification(notification);

        this.result = new ResponseNotificationSaved("Aviso salvo com sucesso.");
    }
}
