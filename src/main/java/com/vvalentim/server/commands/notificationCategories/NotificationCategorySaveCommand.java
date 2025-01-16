package com.vvalentim.server.commands.notificationCategories;

import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategorySave;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategorySave;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class NotificationCategorySaveCommand extends Command {
    private final RequestNotificationCategorySave payload;

    public NotificationCategorySaveCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationCategorySave) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_CATEGORY_SAVE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);

        if (!isOnline) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_CATEGORY_SAVE.jsonKey);
            return;
        }

        if (this.payload.category.getId() != 0) {
            NotificationCategory category = db.findNotificationCategory(this.payload.category.getId());

            // If an id is specified but the category has not been created yet
            if (category == null) {
                this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_CATEGORY_SAVE, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
                return;
            }
        }

        db.saveNotificationCategory(this.payload.category);

        this.result = new ResponseNotificationCategorySave("Categoria salva com sucesso.");
    }
}
