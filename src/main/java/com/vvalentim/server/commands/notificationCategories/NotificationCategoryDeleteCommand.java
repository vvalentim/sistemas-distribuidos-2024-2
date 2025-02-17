package com.vvalentim.server.commands.notificationCategories;

import com.vvalentim.models.Notification;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryDelete;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.notificationCategories.ResponseNotificationCategoryDeleted;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

import java.util.List;

public class NotificationCategoryDeleteCommand extends Command {
    private final RequestNotificationCategoryDelete payload;

    public NotificationCategoryDeleteCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestNotificationCategoryDelete) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.NOTIFICATION_CATEGORY_DELETE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(this.payload.token);

        if (!isOnline || !isSuperUser) {
            this.result = new ResponseUnauthorized(RequestType.NOTIFICATION_CATEGORY_DELETE.jsonKey);
            return;
        }

        NotificationCategory category = db.findNotificationCategory(this.payload.categoryId);

        if (category == null) {
            this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_CATEGORY_DELETE, ErrorType.NOTIFICATION_CATEGORY_NOT_FOUND);
            return;
        }

        List<Notification> notificationsWithCategoryId = db.fetchNotificationsWithCategory(category.getId());

        if (!notificationsWithCategoryId.isEmpty()) {
            this.result = new ResponseResourceNotFound(RequestType.NOTIFICATION_CATEGORY_DELETE, ErrorType.NOTIFICATION_CATEGORY_HAS_ASSOCIATION);
            return;
        }

        db.deleteNotificationCategory(this.payload.categoryId);

        this.result = new ResponseNotificationCategoryDeleted("Exclusão realizada com sucesso.");
    }
}
